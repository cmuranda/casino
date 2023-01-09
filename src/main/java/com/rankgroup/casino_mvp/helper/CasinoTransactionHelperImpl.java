package com.rankgroup.casino_mvp.helper;

import com.rankgroup.casino_mvp.entity.Balance;
import com.rankgroup.casino_mvp.response_entity.BalanceUpdateResponse;
import com.rankgroup.casino_mvp.entity.Player;
import com.rankgroup.casino_mvp.entity.Transaction;
import com.rankgroup.casino_mvp.error.InvalidAmountException;
import com.rankgroup.casino_mvp.error.InvalidWagerException;
import com.rankgroup.casino_mvp.error.PlayerNotFoundException;
import com.rankgroup.casino_mvp.repository.BalanceRepository;
import com.rankgroup.casino_mvp.repository.PlayerRepository;
import com.rankgroup.casino_mvp.repository.TransactionRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@NoArgsConstructor
@Component
public class CasinoTransactionHelperImpl implements CasinoTransactionHelper{
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    TransactionRepository transactionRepository;
    Optional<Player> playerOpt;
    Optional<Balance> balanceOpt;
    Player player;
    Balance balance;

    Integer playerId;
    Transaction transaction;

    BigDecimal transactionAmount;


    public void initialize(Integer playerId, Transaction transaction) throws PlayerNotFoundException, InvalidAmountException, InvalidWagerException {
        this.playerId = playerId;
        this.playerOpt = playerRepository.findById(playerId);

        this.balanceOpt = balanceRepository.findById(playerId);

        this.transaction = transaction;
        this.validateData();
    }

    public void processTransactionComponents(){
        this.processPlayer();
        this.processBalance();
        this.saveTransaction();
        this.saveBalance();
    }

    private void validateData() throws PlayerNotFoundException, InvalidAmountException, InvalidWagerException {
        this.validatePlayerPresent();
        this.validateTransactionAmount();
    }
    private void processPlayer(){
        this.addPlayerToTransaction();
    }

    private void processBalance(){
        this.updateBalanceAmount();
    }

    private void validatePlayerPresent() throws PlayerNotFoundException {
        if (this.playerOpt.isEmpty())
            throw new PlayerNotFoundException("Player with id "+ playerId + " was not found");
    }

    private void addPlayerToTransaction(){
        this.player = playerOpt.get();
        this.transaction.setPlayer(this.player);
    }

    public void setBalance(){
        Balance balance = balanceOpt.get();
        this.balance = balance;
    }

    public void validateTransactionAmount() throws InvalidAmountException, InvalidWagerException {
        BigDecimal amount = this.transaction.getAmount();
        this.setBalance();

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountException("Amount cannot be zero or negative");
        }

        if(transaction.getTransactionType() == TransactionType.WAGER &&
                amount.compareTo(this.balance.getBalanceAmount()) == 1)
            throw new InvalidWagerException("Amount cannot be more than current balance for a wager");
    }

    public void updateBalanceAmount() {
        BigDecimal amount = transaction.getAmount();
        if(transaction.getTransactionType() == TransactionType.WAGER)
            amount = amount.multiply(BigDecimal.valueOf(-1));

        BigDecimal balance_amount = this.balance.getBalanceAmount();
        this.balance.setBalanceAmount(balance_amount.add(amount));
    }


    public void saveTransaction() {
        this.transactionRepository.save(transaction);
    }

    public void saveBalance(){
        this.balanceRepository.save(this.balance);
    }

    public BalanceUpdateResponse getResponseData(){
        BalanceUpdateResponse response = BalanceUpdateResponse.builder()
                .transactionId(this.transaction.getTransactionId())
                .balance(this.balance.getBalanceAmount())
                .build();
        return response;
    }

    }
