package com.rankgroup.casino_mvp.repository;

import com.rankgroup.casino_mvp.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player getPlayerByUsername(String username);
}
