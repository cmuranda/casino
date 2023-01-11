package com.rankgroup.casino_mvp.config;

import com.rankgroup.casino_mvp.entity.Player;
import com.rankgroup.casino_mvp.entity.Transaction;
import com.rankgroup.casino_mvp.helper.TransactionType;
import com.rankgroup.casino_mvp.service.CasinoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class AppConfig {
    @Bean
    CommandLineRunner commandLineRunner(CasinoService casinoService){
        return args -> {
            Player player1 = Player.builder()
                    .username("charles")
                    .build();

            Player player2 = Player.builder()
                    .username("john")
                    .build();

            casinoService.addPlayer(player1);
            casinoService.addPlayer(player2);

            Transaction player1Transaction1 = Transaction.builder()
                    .transactionType(TransactionType.WIN)
                    .player(player1)
                    .amount(BigDecimal.valueOf(100))
                    .build();

            Transaction player1Transaction2 = Transaction.builder()
                    .transactionType(TransactionType.WAGER)
                    .player(player1)
                    .amount(BigDecimal.valueOf(10))
                    .build();

            Transaction player1Transaction3 = Transaction.builder()
                    .transactionType(TransactionType.WAGER)
                    .player(player1)
                    .amount(BigDecimal.valueOf(5))
                    .build();

            casinoService.processCasinoTransaction(1, player1Transaction1);
            casinoService.processCasinoTransaction(1, player1Transaction2);
            casinoService.processCasinoTransaction(1, player1Transaction3);
        };
    }
}
