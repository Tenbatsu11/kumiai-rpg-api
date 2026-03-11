package fr.kumiaigorpg.api.controller;

import fr.kumiaigorpg.api.pojo.JlptLvl;
import fr.kumiaigorpg.api.pojo.Kanji;
import fr.kumiaigorpg.api.repository.KanjiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kanji")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class KanjiController {

    private final KanjiRepository repository;

    @GetMapping
    public List<Kanji> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kanji> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/jlptlvl/{jlptlvl}")
    public List<Kanji> getByJlptLvl(@PathVariable JlptLvl jlptlvl) {
        return repository.findByJlptlvl(jlptlvl);
    }

    //Recherche d'un kanji par son écriture
    @GetMapping("/search")
    public List<Kanji> search (@RequestParam String kanjiName) {
        return repository.findByKanjiName(kanjiName);
    }

    @GetMapping("/description/{description}")
    public List<Kanji> getByDescription(@PathVariable String description) {
        return repository.findByDescription(description);
    }

}
