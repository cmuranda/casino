package com.rankgroup.casino_mvp.controller;

import com.rankgroup.casino_mvp.dto.PlayerTransactionDTO;
import com.rankgroup.casino_mvp.entity.*;
import com.rankgroup.casino_mvp.error.InvalidAmountException;
import com.rankgroup.casino_mvp.error.InvalidWagerException;
import com.rankgroup.casino_mvp.error.PlayerNotFoundException;
import com.rankgroup.casino_mvp.response_entity.BalanceUpdateResponse;
import com.rankgroup.casino_mvp.response_entity.PlayerBalanceResponse;
import com.rankgroup.casino_mvp.service.CasinoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/casino")
public class CasinoController {

    @Autowired
    private CasinoService casinoService;


    @PostMapping(path="/player/{playerId}/balance/update")
    public BalanceUpdateResponse addTransaction(@PathVariable("playerId")Integer playerId, @RequestBody @Valid Transaction transaction)
            throws PlayerNotFoundException, InvalidAmountException, InvalidWagerException {
        return casinoService.processCasinoTransaction(playerId, transaction);
    }

    @PostMapping(path = "/player/add")
    public void addPlayer(@RequestBody Player player){
        casinoService.addPlayer(player);
    }

    @GetMapping(path = "/player/{playerId}/balance")
    public PlayerBalanceResponse getPlayerBalance(@PathVariable("playerId") Integer playerId) throws PlayerNotFoundException {
        return casinoService.getPlayerBalance(playerId);
    }

    @PostMapping(path = "admin/player/transactions")
    public List<PlayerTransaction> getPlayerTransactions(@RequestBody PlayerTransactionDTO username) throws PlayerNotFoundException {
        return casinoService.getPlayerTransactions(username);
    }

}
