package ro.ubbcluj.tpjad.jadbackend;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AuthTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Tag("JAD-2")
    void login_success() {
        webTestClient.post()
            .uri("/api/users/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\": \"alex\", \"password\": \"alex\"}")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                .jsonPath("$.token").isNotEmpty()
                .jsonPath("$.expiresIn").isEqualTo(3600000);
    }

    @Test
    @Tag("JAD-2")
    void login_failed_invalidCredentials() {
        webTestClient.post()
            .uri("/api/users/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\": \"alex\", \"password\": \"testpass\"}")
            .exchange()
            .expectStatus().isUnauthorized()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                .json(
                    "{\"errorMessage\": \"The username or password is incorrect\", \"details\": \"Bad credentials\"}",
                    JsonCompareMode.LENIENT
                );
    }

    @Test
    @Tag("JAD-2")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void register_success() {
        webTestClient.post()
            .uri("/api/users/register")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\": \"alexandrescu\", \"password\": \"alexandrescu\", \"confirmPassword\": \"alexandrescu\"}")
            .exchange()
            .expectStatus().isCreated()
            .expectBody().isEmpty();
    }

    @Test
    @Tag("JAD-2")
    void register_failed_existingUser() {
        webTestClient.post()
            .uri("/api/users/register")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\": \"alex\", \"password\": \"alex\", \"confirmPassword\": \"alex\"}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                .json(
                    "{\"errorMessage\": \"The given username already exists\"}",
                    JsonCompareMode.LENIENT
                );
    }

    @Test
    @Tag("JAD-2")
    void register_failed_passwordMismatch() {
        webTestClient.post()
            .uri("/api/users/register")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\": \"alexandrescu\", \"password\": \"alexandrescu\", \"confirmPassword\": \"andrei\"}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                .json(
                    "{\"errorMessage\": \"Password mismatch\"}",
                    JsonCompareMode.LENIENT
                );
    }
}
