package fr.kumiaigorpg.api.repository;

import fr.kumiaigorpg.api.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(String id);
    Optional<Users> findByUsername(String username);
}
