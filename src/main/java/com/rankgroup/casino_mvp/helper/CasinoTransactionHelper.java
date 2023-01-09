package com.rankgroup.casino_mvp.helper;

import com.rankgroup.casino_mvp.response_entity.BalanceUpdateResponse;
import com.rankgroup.casino_mvp.entity.Transaction;
import com.rankgroup.casino_mvp.error.InvalidAmountException;
import com.rankgroup.casino_mvp.error.InvalidWagerException;
import com.rankgroup.casino_mvp.error.PlayerNotFoundException;

public interface CasinoTransactionHelper {
    public void initialize(Integer playerId, Transaction transaction)
            throws PlayerNotFoundException, InvalidAmountException, InvalidWagerException;

    public void processTransactionComponents();
    public BalanceUpdateResponse getResponseData();
}
