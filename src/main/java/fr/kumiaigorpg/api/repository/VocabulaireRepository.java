package fr.kumiaigorpg.api.repository;

import fr.kumiaigorpg.api.pojo.JlptLvl;
import fr.kumiaigorpg.api.pojo.Vocabulaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabulaireRepository extends JpaRepository<Vocabulaire, Long> {
    List<Vocabulaire> findByJlptlvl(JlptLvl jlptlvl);
    List<Vocabulaire> findByWord(String word);
    List<Vocabulaire> findAll();
    List<Vocabulaire> findByTraduction(String traduction);
}
