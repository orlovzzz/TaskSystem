package org.diploma.projectservice.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.diploma.projectservice.ProjectServiceApplication;
import org.diploma.projectservice.adapter.repository.ProjectRepository;
import org.diploma.projectservice.adapter.rest.dto.CreateProjectDto;
import org.diploma.projectservice.adapter.rest.dto.ProjectDto;
import org.diploma.projectservice.adapter.rest.dto.ProjectUserDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.diploma.projectservice.entity.enums.Permission.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProjectServiceApplication.class)
@AutoConfigureMockMvc
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
@ActiveProfiles("test")
class ProjectControllerTest {
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");
    private final MockMvc mockMvc;
    private final ProjectRepository projectRepository;
    private final JwtTestUtils jwtTestUtils = new JwtTestUtils();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() {
        container.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @AfterAll
    static void tearDown() {
        container.close();
    }

    @Test
    @Sql(scripts = "/dbdata/truncate_data.sql", executionPhase = AFTER_TEST_METHOD)
    void createProject() throws Exception {
        var dto = CreateProjectDto.builder().name("Project").description("test").build();
        mockMvc.perform(post("/api/project")
                        .contentType(APPLICATION_JSON)
                        .with(jwtTestUtils.jwt())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
        var project = projectRepository.findProjectByName("Project").get();
        assertEquals("Project", project.getName());
        assertEquals("test", project.getDescription());
        assertEquals("test", project.getOwner());
    }

    @Test
    @Sql(scripts = "/dbdata/insert_projects.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/dbdata/truncate_data.sql", executionPhase = AFTER_TEST_METHOD)
    void updateProject() throws Exception {
        var projectDto = ProjectDto.builder()
                .name("test1")
                .description("test1")
                .tasks(List.of(1L, 2L, 3L))
                .users(List.of(ProjectUserDto.builder().login("test1").permission(USER).build()))
                .build();
        mockMvc.perform(put("/api/project/1000002")
                        .with(jwtTestUtils.jwt())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto))
                )
                .andExpect(status().isOk());
        var project = projectRepository.findById(1000002L).get();
        assertEquals("test1", project.getName());
        assertEquals("test1", project.getDescription());
        assertEquals(3, project.getTasks().size());
        assertEquals(2, project.getUsers().size());
    }

    @Test
    @Sql(scripts = "/dbdata/insert_projects.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/dbdata/truncate_data.sql", executionPhase = AFTER_TEST_METHOD)
    void addUsersToProject() throws Exception {
        var usersDto = List.of(
                ProjectUserDto.builder().login("test5").build(),
                ProjectUserDto.builder().login("test6").build(),
                ProjectUserDto.builder().login("test7").build()
        );
        mockMvc.perform(post("/api/project/users/1000003")
                        .with(jwtTestUtils.jwt())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usersDto))
                )
                .andExpect(status().isOk());
        var project = projectRepository.findById(1000003L).get();
        assertEquals(3, project.getUsers().size());
    }

    @Test
    @Sql(scripts = "/dbdata/insert_projects.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/dbdata/truncate_data.sql", executionPhase = AFTER_TEST_METHOD)
    void deleteUsersFromProject() throws Exception {
        var usersDto = List.of(
                ProjectUserDto.builder().login("test11").build(),
                ProjectUserDto.builder().login("test10").build()
        );
        mockMvc.perform(delete("/api/project/users/1000004")
                        .with(jwtTestUtils.jwt())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usersDto))
                )
                .andExpect(status().isNoContent());
        var project = projectRepository.findById(1000004L).get();
        assertEquals(1, project.getUsers().size());
    }
}