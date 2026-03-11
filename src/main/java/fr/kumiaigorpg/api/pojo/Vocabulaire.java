package fr.kumiaigorpg.api.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "vocabulaire")
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String word;

    @Column
    private String furigana;

    @Column
    private String traduction;

    @Column(name="jlptlvl")
    @Enumerated(EnumType.STRING)
    private JlptLvl jlptlvl;

}
