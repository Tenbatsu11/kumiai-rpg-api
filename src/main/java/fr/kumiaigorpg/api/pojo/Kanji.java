package fr.kumiaigorpg.api.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "kanji")
@NoArgsConstructor
@AllArgsConstructor
public class Kanji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kanji_name")
    private String kanjiName;

    @Column
    private String kunyomi;

    @Column
    private String onyomi;

    @Column
    private String description;

    @Column(name="jlptlvl")
    @Enumerated(EnumType.STRING)
    private JlptLvl jlptlvl;

}
