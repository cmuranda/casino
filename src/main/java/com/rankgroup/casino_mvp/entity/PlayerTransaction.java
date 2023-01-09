package com.rankgroup.casino_mvp.entity;

import com.rankgroup.casino_mvp.helper.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@Builder
public class PlayerTransaction {
    private TransactionType transactionType;
    private BigInteger transactionId;
    private BigDecimal amount;
}
