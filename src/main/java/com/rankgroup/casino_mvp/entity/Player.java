package com.rankgroup.casino_mvp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.Date;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer playerId;

    @Column(unique = true, name = "username")
    @ColumnTransformer(write = "LOWER(?)")
    @NotNull
    private String username;

    private Date created;

    @OneToMany(mappedBy = "player")
    private List<Transaction> transactions;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Balance balance;

    @PrePersist
    void onCreate(){this.created = new Date();}
}
