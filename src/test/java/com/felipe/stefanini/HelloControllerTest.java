package com.felipe.stefanini;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "client_user")
    void shouldReturnWordForClientUser() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("word"));
    }

    @Test
    @WithMockUser(roles = "client_admin")
    void shouldReturnWordAdminForClientAdmin() throws Exception {
        mockMvc.perform(get("/hello/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("word-admin"));
    }

    @Test
    void shouldReturnUnauthorizedWhenNoUser() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }
}
