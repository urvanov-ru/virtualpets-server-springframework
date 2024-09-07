package ru.urvanov.virtualpets.server.controller.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;

/**
 * <p>
 * Тест для {@link PetController}.
 * </p>
 * <p>
 * Пример теста с использованием сессии и подстановкой пользователя,
 * от лица которого выполняется запрос.
 * </p>
 */
class PetControllerTest extends BaseControllerTest {

    private UserDetailsImpl userDetailsImpl = new UserDetailsImpl(1, "test", "Tester",
            "123",
            true,
            List.of(new SimpleGrantedAuthority("ROLE_USER")));
    
    

    @Test
    @Sql({"/ru/urvanov/virtualpets/server/clean.sql",
            "PetControllerTest.sql"})
    void getUserPets() throws Exception {
        mockMvc.perform(get("/api/v1/PetService/getUserPets")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(userDetailsImpl)))
                .andExpectAll(status().isOk(), content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.petsInfo").isArray());
        ;
    }
    
    @Test
    @Sql(
            scripts = {"/ru/urvanov/virtualpets/server/clean.sql",
                    "PetControllerTest.sql"},
            statements = """
            insert into\
                virtualpets_server_springframework.pet_food(\
                    pet_id, food_id, food_count)\
                values (1, 'DRY_FOOD', 10)""")
    void eat() throws Exception {
        MockHttpSession session = new MockHttpSession(
                mockMvc.getDispatcherServlet().getServletContext(),
                "test");
        selectPet(session);
        mockMvc.perform(post("/api/v1/PetService/satiety")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "foodId": "DRY_FOOD"
                        
                        }""")
                .with(user(userDetailsImpl))
                .session(session)
                )
                .andExpectAll(status().isNoContent());
        ;
    }
    
    private void selectPet(MockHttpSession session) throws Exception {
        mockMvc.perform(post("/api/v1/PetService/select")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "petId": 1
                        
                        }""")
                .with(user(userDetailsImpl))
                .session(session)
                )
                .andExpectAll(status().isNoContent());
    }
}
