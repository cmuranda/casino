package com.rankgroup.casino_mvp.service;

import com.rankgroup.casino_mvp.dto.PlayerTransactionDTO;
import com.rankgroup.casino_mvp.entity.*;
import com.rankgroup.casino_mvp.error.InvalidAmountException;
import com.rankgroup.casino_mvp.error.InvalidWagerException;
import com.rankgroup.casino_mvp.error.PlayerNotFoundException;
import com.rankgroup.casino_mvp.response_entity.BalanceUpdateResponse;
import com.rankgroup.casino_mvp.response_entity.PlayerBalanceResponse;

import java.util.List;

public interface CasinoService {
    BalanceUpdateResponse processCasinoTransaction(Integer playerId, Transaction transaction) throws PlayerNotFoundException, InvalidAmountException, InvalidWagerException;

    void addPlayer(Player player);

    PlayerBalanceResponse getPlayerBalance(Integer playerId) throws PlayerNotFoundException;

    List<PlayerTransaction> getPlayerTransactions(PlayerTransactionDTO username) throws PlayerNotFoundException;
}
