package ru.urvanov.virtualpets.server.controller.api;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

/**
 * Базовый пример теста MockMvc
 */
class PublicControllerTest extends BaseControllerTest {

    @Test
    void getServerTechnicalInfo() throws Exception {
        mockMvc.perform(get(
                "/api/v1/PublicService/serverTechnicalInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.info").isMap())
                .andExpect(jsonPath("$.info['java.version']")
                        .isString());
        ;
    }
}
