package com.rankgroup.casino_mvp.service;

import com.rankgroup.casino_mvp.entity.Player;
import com.rankgroup.casino_mvp.entity.Transaction;
import com.rankgroup.casino_mvp.error.PlayerNotFoundException;
import com.rankgroup.casino_mvp.helper.TransactionType;
import com.rankgroup.casino_mvp.repository.PlayerRepository;
import jdk.jfr.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CasinoServiceTest {
    @Autowired
    CasinoService underTest;

    @Mock
    PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        underTest = new CasinoServiceImpl(playerRepository);
    }

    @Test
    public void itShouldAddPlayer(){
        Player newPlayer = Player.builder().build();
        Player playerWithUsername = Player.builder()
                        .username("charles")
                                .build();

        //when
        underTest.addPlayer(newPlayer);
        //then
        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(playerArgumentCaptor.capture());

        Player playerToBeAdded = playerArgumentCaptor.getValue();

        assertThat(newPlayer).isEqualTo(playerToBeAdded);

    }

    @Test
    @DisplayName("Should raise an error when trying to save transaction for player who is not in the database")
    public void itShouldRaisePlayerNotFoundErrorForPlayerNotAlreadyInTheDatabase(){
        Integer playerId = 1000000;
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.WIN)
                .amount(BigDecimal.TEN)
                .build();

        Assertions.assertThrows(Exception.class, ()->{
            underTest.processCasinoTransaction(playerId, transaction);
        });
    }
}