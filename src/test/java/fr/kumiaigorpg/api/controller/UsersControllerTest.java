package fr.kumiaigorpg.api.controller;

import fr.kumiaigorpg.api.pojo.Abonnement;
import fr.kumiaigorpg.api.pojo.JlptLvl;
import fr.kumiaigorpg.api.pojo.Users;
import fr.kumiaigorpg.api.repository.UsersRepository;
import fr.kumiaigorpg.api.security.JwtFilter;
import fr.kumiaigorpg.api.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsersControllerTest {

    @Autowired MockMvc mockMvc;
    @MockitoBean UsersRepository repo;
    @MockitoBean  PasswordEncoder passwordEncoder;
    @MockitoBean  JwtService jwtService;
    @MockitoBean  JwtFilter jwtFilter;

    private Users makeUser() {
        Users u = new Users();
        u.setId("abc-123");
        u.setEmail("test@test.com");
        u.setPassword("$2a$10$hashedpwd");
        u.setUsername("Taro");
        u.setUserlvl(JlptLvl.N5);
        u.setAbonnement(Abonnement.GRATUIT);
        return u;
    }

    @Test
    void login_succes() throws Exception {
        when(repo.findByEmail("test@test.com"))
                .thenReturn(Optional.of(makeUser()));
        when(passwordEncoder.matches("password123", "$2a$10$hashedpwd"))
                .thenReturn(true);
        when(jwtService.generateToken("test@test.com"))
                .thenReturn("fake-token");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-token"))
                .andExpect(jsonPath("$.username").value("Taro"));
    }

    @Test
    void login_mot_de_passe_incorrect() throws Exception {
        when(repo.findByEmail("test@test.com"))
                .thenReturn(Optional.of(makeUser()));
        when(passwordEncoder.matches("mauvais", "$2a$10$hashedpwd"))
                .thenReturn(false);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"mauvais\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_email_introuvable() throws Exception {
        when(repo.findByEmail("inconnu@test.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"inconnu@test.com\",\"password\":\"pwd\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_user_valide() throws Exception {
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$hashedpwd");
        when(repo.save(any())).thenReturn(makeUser());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"password123\"," +
                                "\"username\":\"Taro\",\"userlvl\":\"N5\",\"abonnement\":\"GRATUIT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Taro"));
    }

    @Test
    void update_abonnement_premium() throws Exception {
        Users u = makeUser();
        when(repo.findById("abc-123")).thenReturn(Optional.of(u));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(put("/api/users/abc-123/abonnement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"abonnement\":\"PREMIUM\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abonnement").value("PREMIUM"));
    }

    @Test
    void update_abonnement_user_introuvable() throws Exception {
        when(repo.findById("inexistant")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/users/inexistant/abonnement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"abonnement\":\"PREMIUM\"}"))
                .andExpect(status().isNotFound());
    }
}