package fr.kumiaigorpg.api.controller;

import fr.kumiaigorpg.api.pojo.JlptLvl;
import fr.kumiaigorpg.api.pojo.Vocabulaire;
import fr.kumiaigorpg.api.repository.VocabulaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/vocabulaire")
@RequiredArgsConstructor
public class VocabulaireController {

    private final VocabulaireRepository repository;

    @GetMapping
    public List<Vocabulaire> getAll() {

        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vocabulaire> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/jlptlvl/{jlptlvl}")
    public List<Vocabulaire> getByJlptLvl(@PathVariable JlptLvl jlptlvl) {
        return repository.findByJlptlvl(jlptlvl);
    }

    @GetMapping("/search")
    public List<Vocabulaire> search(@RequestParam String word) {
        return repository.findByWord(word);
    }

    @GetMapping("/traduction/{traduction}")
    public List<Vocabulaire> getByTraduction(@PathVariable String traduction) {
        return repository.findByTraduction(traduction);
    }

}
