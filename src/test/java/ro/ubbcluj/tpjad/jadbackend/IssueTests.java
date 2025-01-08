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
public class IssueTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Disabled
    @Tag("JAD-5")
    void getAllIssues_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.get()
            .uri("/api/projects/1/issues")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "[" +
                    "{\"id\": 1, \"summary\": \"Issue 1\", \"description\": \"Description for Issue 1\", \"type\": \"BUG\", \"status\": \"TODO\", \"date\": \"2024-06-12 18:00\", \"assignee\": \"ioana\"}," +
                    "{\"id\": 2, \"summary\": \"Issue 2\", \"description\": \"Description for Issue 2\", \"type\": \"STORY\", \"status\": \"DOING\", \"date\": \"2024-06-13 13:00\", \"assignee\": \"mihai\"}" +
                "]",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    void getAllIssues_success_emptyList() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.get()
            .uri("/api/projects/3/issues")
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
    @Tag("JAD-5")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createIssue_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.post()
            .uri("/api/projects/1/issues")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"summary\": \"New Issue\", \"description\": \"Description for New Issue\", \"type\": \"BUG\", \"status\": \"TODO\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}")
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"id\": 5, \"summary\": \"New Issue\", \"description\": \"Description for New Issue\", \"type\": \"BUG\", \"status\": \"TODO\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    void createIssue_failed_invalidType() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.post()
            .uri("/api/projects/1/issues")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"summary\": \"New Issue\", \"description\": \"Description for New Issue\", \"type\": \"ceva\", \"status\": \"TODO\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Invalid type provided\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    void createIssue_failed_invalidDate() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.post()
            .uri("/api/projects/1/issues")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"summary\": \"New Issue\", \"description\": \"Description for New Issue\", \"type\": \"BUG\", \"status\": \"TODO\", \"date\": \"2025 01 08 10:00\", \"assignee\": \"ioana\"}")
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
    @Tag("JAD-5")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateIssue_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.put()
            .uri("/api/projects/1/issues/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"summary\": \"Issue 1 updated\", \"description\": \"Description for Issue 1 updated\", \"type\": \"BUG\", \"status\": \"DOING\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"id\": 1, \"summary\": \"Issue 1 updated\", \"description\": \"Description for Issue 1 updated\", \"type\": \"BUG\", \"status\": \"DOING\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    void updateIssue_failed_invalidAssignee() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.put()
            .uri("/api/projects/1/issues/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"summary\": \"Issue 1 updated\", \"description\": \"Description for Issue 1 updated\", \"type\": \"BUG\", \"status\": \"DOING\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"cineva\"}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Invalid assignee provided\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    void updateIssue_failed_invalidStatus() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.put()
            .uri("/api/projects/1/issues/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"summary\": \"Issue 1 updated\", \"description\": \"Description for Issue 1 updated\", \"type\": \"BUG\", \"status\": \"maybe\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Invalid status provided\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    void updateIssue_failed_issueNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.put()
            .uri("/api/projects/1/issues/10")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"summary\": \"Issue 1 updated\", \"description\": \"Description for Issue 1 updated\", \"type\": \"BUG\", \"status\": \"DOING\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'Issue' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteIssue_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.delete()
            .uri("/api/projects/1/issues/1")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNoContent()
            .expectBody().isEmpty();
    }

    @Test
    @Disabled
    @Tag("JAD-5")
    void deleteIssue_failed_issueNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.delete()
            .uri("/api/projects/1/issues/10")
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
