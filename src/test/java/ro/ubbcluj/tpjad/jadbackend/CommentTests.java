package ro.ubbcluj.tpjad.jadbackend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.reactive.server.WebTestClient;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserLoginGetDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class CommentTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Disabled
    @Tag("JAD-7")
    void getAllComments_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.get()
            .uri("/api/issues/1/comments")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "[" +
                    "{\"id\": 1, \"content\": \"Comment 1\", \"date\": \"2024-06-13 10:00\", \"username\": \"alex\"}" +
                "]",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    void getAllComments_success_emptyList() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.get()
            .uri("/api/issues/2/comments")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "[]",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createComment_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.post()
            .uri("/api/issues/1/comments")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"content\": \"New comment\", \"date\": \"2025-01-08 10:00\"}")
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"id\": 3, \"content\": \"New comment\", \"date\": \"2025-01-08 10:00\", \"username\": \"mihai\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    void createComment_failed_invalidDate() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.post()
            .uri("/api/issues/1/comments")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"content\": \"New comment\", \"date\": \"2025 01 08 10:00\"}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Invalid date provided\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateComment_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The author of the comment we modify

        webTestClient.put()
            .uri("/api/issues/1/comments/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"content\": \"Comment 1 updated\", \"date\": \"2025-01-08 10:00\"}")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"id\": 1, \"content\": \"Comment 1 updated\", \"date\": \"2025-01-08 10:00\", \"username\": \"alex\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    void updateComment_failed_invalidDate() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The author of the comment we modify

        webTestClient.put()
            .uri("/api/issues/1/comments/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"content\": \"Comment 1 updated\", \"date\": \"2025 01 08 10:00\"}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Invalid date provided\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    void updateComment_failed_commentNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The author of the comment we modify

        webTestClient.put()
            .uri("/api/issues/1/comments/10")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"content\": \"Comment 1 updated\", \"date\": \"2025-01-08 10:00\"}")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'Comment' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteComment_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The author of the comment we delete

        webTestClient.delete()
            .uri("/api/issues/1/comments/1")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNoContent()
            .expectBody().isEmpty();
    }

    @Test
    @Disabled
    @Tag("JAD-7")
    void deleteComment_failed_commentNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The author of the comment we delete

        webTestClient.delete()
            .uri("/api/issues/1/comments/10")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'Comment' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }
}
