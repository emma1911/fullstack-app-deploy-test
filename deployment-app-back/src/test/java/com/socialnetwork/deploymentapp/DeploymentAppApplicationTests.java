package com.socialnetwork.deploymentapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DeploymentAppApplicationTests {

    @Test
    void contextLoads() {
        // Verifies that the Spring application context loads successfully
    }
}

// ==================== Controller Tests ====================

@WebMvcTest(HelloController.class)
class HelloControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHelloMessage() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Spring Boot! 👋"));
    }

    @Test
    void shouldReturnMessageObject() throws Exception {
        mockMvc.perform(get("/api/message"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Welcome to our test project!"))
                .andExpect(jsonPath("$.content").value("This is working perfectly."));
    }

    @Test
    void shouldReturnMessageWithCorrectStructure() throws Exception {
        mockMvc.perform(get("/api/message"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists());
    }
}