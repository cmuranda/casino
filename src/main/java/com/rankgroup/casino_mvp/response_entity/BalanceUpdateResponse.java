package com.rankgroup.casino_mvp.response_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
public class BalanceUpdateResponse {
    private BigInteger transactionId;
    private BigDecimal balance;
}
