package com.rankgroup.casino_mvp.response_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class PlayerBalanceResponse {
    private Integer playerId;
    private BigDecimal balance;
}
