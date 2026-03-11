package fr.kumiaigorpg.api.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name="user_lvl",nullable = false)
    @Enumerated(EnumType.STRING)
    private JlptLvl userlvl;

    @Column(name="abonnement", nullable = false)
    @Enumerated(EnumType.STRING)
    private Abonnement abonnement;


}
