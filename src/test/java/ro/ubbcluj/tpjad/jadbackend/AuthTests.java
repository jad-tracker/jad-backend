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

    @Test
    @Tag("JAD-1")
    void getAllProjects_failed_unauthorized() {
        webTestClient.get()
            .uri("/api/projects")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void createProject_failed_unauthorized() {
        webTestClient.post()
            .uri("/api/projects")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"name\": \"New Project\", \"description\": \"Description for New Project\"}")
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void updateProjectName_failed_unauthorized() {
        webTestClient.put()
            .uri("/api/projects/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"name\": \"Project 1 updated\"}")
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void deleteProject_failed_unauthorized() {
        webTestClient.delete()
            .uri("/api/projects/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void getAllProjectMembers_failed_unauthorized() {
        webTestClient.get()
            .uri("/api/projects/1/members")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void addProjectMember_failed_unauthorized() {
        webTestClient.post()
            .uri("/api/projects/1/members")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\": \"maria\", \"role\": \"Devops\"}")
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void removeProjectMember_failed_unauthorized() {
        webTestClient.delete()
            .uri("/api/projects/1/members/4")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void getAllIssues_failed_unauthorized() {
        webTestClient.get()
            .uri("/api/projects/1/issues")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void createIssue_failed_unauthorized() {
        webTestClient.post()
            .uri("/api/projects/1/issues")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"summary\": \"New Issue\", \"description\": \"Description for New Issue\", \"type\": \"BUG\", \"status\": \"TODO\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}")
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void updateIssue_failed_unauthorized() {
        webTestClient.put()
            .uri("/api/projects/1/issues/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"summary\": \"Issue 1 updated\", \"description\": \"Description for Issue 1 updated\", \"type\": \"BUG\", \"status\": \"DOING\", \"date\": \"2025-01-08 10:00\", \"assignee\": \"ioana\"}")
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void deleteIssue_failed_unauthorized() {
        webTestClient.delete()
            .uri("/api/projects/1/issues/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void getAllComments_failed_unauthorized() {
        webTestClient.get()
            .uri("/api/issues/1/comments")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void createComment_failed_unauthorized() {
        webTestClient.post()
            .uri("/api/issues/1/comments")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"content\": \"New comment\", \"date\": \"2025-01-08 10:00\"}")
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void updateComment_failed_unauthorized() {
        webTestClient.put()
            .uri("/api/issues/1/comments/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"content\": \"Comment 1 updated\", \"date\": \"2025-01-08 10:00\"}")
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Tag("JAD-1")
    void deleteComment_failed_unauthorized() {
        webTestClient.delete()
            .uri("/api/issues/1/comments/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isForbidden()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"You are not authorized to access this resource\", \"details\": \"Full authentication is required to access this resource\"}",
                JsonCompareMode.LENIENT
            );
    }
}
