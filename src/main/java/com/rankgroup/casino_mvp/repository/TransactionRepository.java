package com.rankgroup.casino_mvp.repository;

import com.rankgroup.casino_mvp.entity.Player;
import com.rankgroup.casino_mvp.entity.PlayerTransaction;
import com.rankgroup.casino_mvp.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.math.BigInteger;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, BigInteger> {
    List<PlayerTransaction> getTransactionsByPlayer(Player player, Pageable pageable);
}
