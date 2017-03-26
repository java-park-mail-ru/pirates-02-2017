package controllers;

import api.Application;
import api.model.User;
import api.repository.UserRepository;
import api.utils.ErrorCodes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import static org.junit.Assert.*;

import static api.controllers.SessionController.USER_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by Vileven on 26.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT)
public class ValidatorControllerTest {
    Logger logger = LoggerFactory.getLogger(SessionControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository.deleteAll();
        userRepository.save(new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    public void validateLoginLength() throws Exception {
        mockMvc
                .perform(post("/validator/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"a1dsad213r324\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ok"))
        ;

        mockMvc
                .perform(post("/validator/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"a1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;

        mockMvc
                .perform(post("/validator/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"a1123124dsfjdksdlj4k23j4klj3kl4jdskjoi3249235304jtfd9sjf0sej4j302j4\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;
    }

    @Test
    public void validatePasswordLength() throws Exception {
        mockMvc
                .perform(post("/validator/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"a1dsaAd213r324\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ok"))
        ;

        mockMvc
                .perform(post("/validator/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"a1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;

        mockMvc
                .perform(post("/validator/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"a1123124dsfjdksdlj4k23jA4klj3kl4jdskjoi3249235304jtfd9sjf0sej4j302j4\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;
    }

    @Test
    public void validateEmailLength() throws Exception {
        mockMvc
                .perform(post("/validator/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"email1@mail.ru\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ok"))
        ;

        mockMvc
                .perform(post("/validator/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"a1123124dsfksdlj4k23jA4klj3k@l4jdskjoi324923304jt.fd9sjf0sej4j302j4\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;
    }

    @Test
    public void validateLoginExists() throws Exception {
        mockMvc
                .perform(post("/validator/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"sergey\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;
    }

    @Test
    public void validateEmailExists() throws Exception {
        mockMvc
                .perform(post("/validator/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"email@mail.ru\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;
    }

    @Test
    public void validateNotEmail() throws Exception {
        mockMvc
                .perform(post("/validator/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"emailmail.ru\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("error"))
        ;
    }


    /**
     * В идеале, пароль содержит одну строчну, прописную букву и цифру
     */
    @Test
    public void validateWarnigForNotGoodPassword() throws Exception {
        mockMvc
                .perform(post("/validator/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"asdhajsdasd\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("warning"))
        ;

        mockMvc
                .perform(post("/validator/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"1231243144\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("warning"))
        ;

        mockMvc
                .perform(post("/validator/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"HFAJDFHKJAF\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("warning"))
        ;

        mockMvc
                .perform(post("/validator/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"fjdskfaJKFDF123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ok"))
        ;
    }


}
