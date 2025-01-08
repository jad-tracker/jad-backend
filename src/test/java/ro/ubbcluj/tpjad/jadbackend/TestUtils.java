package ro.ubbcluj.tpjad.jadbackend;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserLoginGetDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUtils {
    public static UserLoginGetDto loginUser(WebTestClient webTestClient, String username, String password) {
        UserLoginGetDto tokenDetails = webTestClient.post()
            .uri("/api/users/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password))
            .exchange()
            .returnResult(UserLoginGetDto.class)
            .getResponseBody()
            .blockFirst();

        assertNotNull(tokenDetails);

        return tokenDetails;
    }

    public static UserLoginGetDto registerAndLoginUser(WebTestClient webTestClient, String username, String password) {
        webTestClient.post()
            .uri("/api/users/register")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(String.format("{\"username\": \"%s\", \"password\": \"%s\", \"confirmPassword\": \"%s\"}", username, password, password))
            .exchange()
            .expectStatus().isCreated()
            .expectBody().isEmpty();

        UserLoginGetDto tokenDetails = webTestClient.post()
            .uri("/api/users/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password))
            .exchange()
            .returnResult(UserLoginGetDto.class)
            .getResponseBody()
            .blockFirst();

        assertNotNull(tokenDetails);

        return tokenDetails;
    }
}
