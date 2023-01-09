package com.rankgroup.casino_mvp.entity;

import com.rankgroup.casino_mvp.helper.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger transactionId;

    @ManyToOne
    @JoinColumn(name = "playerId",
            referencedColumnName = "playerId",
            nullable = false)
    private Player player;

    @NotNull(message = "Transaction type is required")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount cannot be negative")
    private BigDecimal amount;
    private Date created;

    private Transaction(){}

    public BigInteger getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(BigInteger transactionId) {
        this.transactionId = transactionId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", player=" + player +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", created=" + created +
                '}';
    }

    @PrePersist
    void OnCreate(){
        this.created = new Date();
    }
}
