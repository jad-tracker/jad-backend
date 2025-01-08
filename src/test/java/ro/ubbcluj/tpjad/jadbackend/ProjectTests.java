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
public class ProjectTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Disabled
    @Tag("JAD-3")
    void getAllProjects_success_memberAndLeader() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex");

        webTestClient.get()
            .uri("/api/projects")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "[" +
                    "{\"id\": 1, \"name\": \"Project 1\", \"description\": \"Description of Project 1\", \"lead\": \"alex\"}," +
                    "{\"id\": 2, \"name\": \"Project 2\", \"description\": \"Description of Project 2\", \"lead\": \"maria\"}," +
                    "{\"id\": 3, \"name\": \"Project 3\", \"description\": \"Description of Project 3\", \"lead\": \"alex\"}" +
                "]",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void getAllProjects_success_memberOnly() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.get()
            .uri("/api/projects")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "[" +
                    "{\"id\": 1, \"name\": \"Project 1\", \"description\": \"Description of Project 1\", \"lead\": \"alex\"}," +
                    "{\"id\": 2, \"name\": \"Project 2\", \"description\": \"Description of Project 2\", \"lead\": \"maria\"}" +
                "]",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllProjects_success_emptyList() {
        UserLoginGetDto tokenDetails = TestUtils.registerAndLoginUser(webTestClient, "andrei", "andrei");

        webTestClient.get()
            .uri("/api/projects")
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
    @Tag("JAD-3")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createProject_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai");

        webTestClient.post()
            .uri("/api/projects")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"name\": \"New Project\", \"description\": \"Description for New Project\"}")
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"id\": 4, \"name\": \"New Project\", \"description\": \"Description for New Project\", \"lead\": \"mihai\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateProjectName_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project we modify

        webTestClient.put()
            .uri("/api/projects/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"name\": \"Project 1 updated\"}")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"id\": 1, \"name\": \"Project 1 updated\", \"description\": \"Description of Project 1\" \"lead\": \"alex\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void updateProjectName_failed_projectNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project we modify

        webTestClient.put()
            .uri("/api/projects/10")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"name\": \"Project 1 updated\"}")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'Project' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteProject_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project we delete

        webTestClient.delete()
            .uri("/api/projects/1")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNoContent()
            .expectBody().isEmpty();
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void deleteProject_failed_projectNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project we delete

        webTestClient.delete()
            .uri("/api/projects/10")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'Project' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void getAllProjectMembers_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "mihai", "mihai"); // Member or lead of the project

        webTestClient.get()
            .uri("/api/projects/1/members")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "[" +
                    "{\"userId\": 2, \"username\": \"mihai\", \"role\": \"Dev\"}," +
                    "{\"userId\": 4, \"username\": \"ioana\", \"role\": \"QA\"}" +
                "]",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void getAllProjectMembers_success_emptyList() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // Member or lead of the project

        webTestClient.get()
            .uri("/api/projects/3/members")
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
    @Tag("JAD-3")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addProjectMember_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project

        webTestClient.post()
            .uri("/api/projects/1/members")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"username\": \"maria\", \"role\": \"Devops\"}")
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"userId\": 3, \"username\": \"maria\", \"role\": \"Devops\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void addProjectMember_failed_userNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project

        webTestClient.post()
            .uri("/api/projects/1/members")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"username\": \"cineva\", \"role\": \"Devops\"}")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'User' with username 'cineva' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void addProjectMember_failed_projectNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project

        webTestClient.post()
            .uri("/api/projects/10/members")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .bodyValue("{\"username\": \"maria\", \"role\": \"Devops\"}")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'Project' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void removeProjectMember_success() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project

        webTestClient.delete()
            .uri("/api/projects/1/members/4")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNoContent()
            .expectBody().isEmpty();
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void removeProjectMember_failed_userNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project

        webTestClient.delete()
            .uri("/api/projects/1/members/10")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'User' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }

    @Test
    @Disabled
    @Tag("JAD-3")
    void removeProjectMember_failed_projectNotFound() {
        UserLoginGetDto tokenDetails = TestUtils.loginUser(webTestClient, "alex", "alex"); // The lead of the project

        webTestClient.delete()
            .uri("/api/projects/10/members/1")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Bearer %s", tokenDetails.getToken()))
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                "{\"errorMessage\": \"Entity 'Project' with id '10' not found.\"}",
                JsonCompareMode.LENIENT
            );
    }
}
