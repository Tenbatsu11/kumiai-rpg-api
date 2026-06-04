package fr.kumiaigorpg.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // ← utilise application-test.properties
class SecurityIntegrationTest {

    @Autowired MockMvc mockMvc;

    @Test
    void route_publique_accessible_sans_token() throws Exception {
        mockMvc.perform(get("/api/kanji"))
                .andExpect(status().isOk());
    }

    @Test
    void route_protegee_sans_token_retourne_403() throws Exception {
        mockMvc.perform(put("/api/users/abc/abonnement")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content("{\"abonnement\":\"PREMIUM\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void route_protegee_avec_token_invalide_retourne_403() throws Exception {
        mockMvc.perform(put("/api/users/abc/abonnement")
                        .header("Authorization", "Bearer token.invalide.xxx")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content("{\"abonnement\":\"PREMIUM\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void login_accessible_sans_token() throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"pwd\"}"))
                .andExpect(status().isUnauthorized()); // 404 car user inexistant, pas 403
    }
}