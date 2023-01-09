package com.rankgroup.casino_mvp.service;

import com.rankgroup.casino_mvp.dto.PlayerTransactionDTO;
import com.rankgroup.casino_mvp.entity.*;
import com.rankgroup.casino_mvp.error.InvalidAmountException;
import com.rankgroup.casino_mvp.error.InvalidWagerException;
import com.rankgroup.casino_mvp.error.PlayerNotFoundException;
import com.rankgroup.casino_mvp.helper.CasinoTransactionHelper;
import com.rankgroup.casino_mvp.repository.BalanceRepository;
import com.rankgroup.casino_mvp.repository.PlayerRepository;
import com.rankgroup.casino_mvp.repository.TransactionRepository;
import com.rankgroup.casino_mvp.response_entity.BalanceUpdateResponse;
import com.rankgroup.casino_mvp.response_entity.PlayerBalanceResponse;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

@Service
@NoArgsConstructor
public class CasinoServiceImpl implements CasinoService{
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    CasinoTransactionHelper helper;

    public CasinoServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    @Override
    public BalanceUpdateResponse processCasinoTransaction(Integer playerId, Transaction transaction)
            throws PlayerNotFoundException, InvalidAmountException, InvalidWagerException {

        helper.initialize(playerId, transaction);
        helper.processTransactionComponents();

        return helper.getResponseData();
    }

    @Override
    @Transactional
    public void addPlayer(Player player) {
        playerRepository.save(player);

        Balance balance = Balance.builder()
                .player(player)
                        .balanceAmount(BigDecimal.ZERO)
                                .build();

        balanceRepository.save(balance);
    }

    @Override
    public PlayerBalanceResponse getPlayerBalance(Integer playerId) throws PlayerNotFoundException {
        Balance balance = balanceRepository.getBalanceByBalanceId(playerId);
        if(balance == null)
            throw new PlayerNotFoundException("Player with id " + playerId+ " does not exist");
        return PlayerBalanceResponse.builder()
                .playerId(Integer.valueOf(playerId))
                .balance(balance.getBalanceAmount())
                .build();
    }

    @Override
    public List<PlayerTransaction> getPlayerTransactions(PlayerTransactionDTO name) throws PlayerNotFoundException {
        String username = name.getUsername();
        Player player = playerRepository.getPlayerByUsername(username);
        if(player == null)
            throw new PlayerNotFoundException("Player with username " + username+ " does not exist");

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("transactionId")));
        List<PlayerTransaction> playerTransactions = transactionRepository.getTransactionsByPlayer(player, pageable);
        System.out.println(playerTransactions);
        return playerTransactions;
    }

}
