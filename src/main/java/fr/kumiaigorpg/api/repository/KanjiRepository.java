package fr.kumiaigorpg.api.repository;

import fr.kumiaigorpg.api.pojo.JlptLvl;
import fr.kumiaigorpg.api.pojo.Kanji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KanjiRepository extends JpaRepository<Kanji, Long> {
    List<Kanji> findByJlptlvl(JlptLvl jlptlvl);
    List<Kanji> findByKanjiName(String kanjiName);
    List<Kanji> findByDescription(String description);
    List<Kanji> findAll();
}
