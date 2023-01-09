package com.rankgroup.casino_mvp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Balance {
    @Id
    private Integer balanceId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "playerId",
            nullable = false)
    private Player player;

    private BigDecimal balanceAmount;

    @Version
    private Long transactions;

}
