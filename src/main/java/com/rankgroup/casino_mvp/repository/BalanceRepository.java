package com.rankgroup.casino_mvp.repository;

import com.rankgroup.casino_mvp.entity.Balance;
import com.rankgroup.casino_mvp.entity.Player;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Balance findByPlayer(Player player);

    Balance getBalanceByBalanceId(Integer playerId);
}
