package fr.kumiaigorpg.api.controller;

import fr.kumiaigorpg.api.pojo.Abonnement;
import fr.kumiaigorpg.api.pojo.Users;
import fr.kumiaigorpg.api.repository.UsersRepository;
import fr.kumiaigorpg.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class UsersController {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @GetMapping
    public List<Users> getAll(){
        return repository.findAll();
    }

    record LoginRequest(String email, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return repository.findByEmail(request.email())
                .map(user -> {
                    if (passwordEncoder.matches(request.password(), user.getPassword())) {
                        String token = jwtService.generateToken(user.getEmail());
                        String username = user.getUsername() != null ? user.getUsername() : "";
                        String userLvl  = user.getUserlvl()  != null ? user.getUserlvl().toString() : "";
                        String id       = user.getId()        != null ? user.getId() : "";
                        String abonnement = user.getAbonnement() != null ? user.getAbonnement().toString() : "";

                        return ResponseEntity.ok(Map.of(
                                "token", token,
                                "id", user.getId(),
                                "username", user.getUsername(),
                                "userlvl", user.getUserlvl(),
                                "abonnement", user.getAbonnement()
                        ));
                    }
                    return ResponseEntity.status(401).body(Map.of("error", "Mot de passe incorrect"));
                })
                .orElse(ResponseEntity.status(404).body(Map.of("error", "Utilisateur introuvable")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> findById(@PathVariable String id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Users> getByEmail(@PathVariable String email){
        return repository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getByUsername(@PathVariable String username){
        return repository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Users create(@RequestBody Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> update(@PathVariable String id, @RequestBody Users updated ){
        return repository.findById(id).map(u -> {
            u.setUsername(updated.getUsername());
            u.setEmail(updated.getEmail());
            u.setUserlvl(updated.getUserlvl());
            u.setAbonnement(updated.getAbonnement());
            return ResponseEntity.ok(repository.save(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable String id,
                                               @RequestBody Map<String, String> body) {
        return repository.findById(id).map(u -> {
            u.setPassword(passwordEncoder.encode(body.get("password")));
            repository.save(u);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/abonnement")
    public ResponseEntity<?> updateAbonnement(@PathVariable String id,
                                                 @RequestBody Map<String, String> body){
        return repository.findById(id).map(user -> {
            user.setAbonnement(Abonnement.valueOf(body.get("abonnement")));
            repository.save(user);
            return ResponseEntity.ok(Map.of("abonnement", user.getAbonnement().name()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        if (!repository.existsById(id))
            return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
