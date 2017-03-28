package controllers;

import api.Application;
import api.model.User;
import api.repository_old.UserRepository;
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
import org.springframework.test.web.servlet.MvcResult;

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
public class SessionControllerTest {

    Logger logger = LoggerFactory.getLogger(SessionControllerTest.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private Long id;

    @Before
    public void setup() {
        userRepository.deleteAll();
        final User user = userRepository.save(new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now()));
        this.id = user.getId();
    }

    @Test
    public void loginWithUserLoginSuccess() throws Exception {
        final MvcResult result = mockMvc
                .perform(post("/session/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login_or_email\":\"sergey\"," +
                                "\"password\":\"qwerty123\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
                .andReturn()
        ;

        assertEquals(this.id, result.getRequest().getSession().getAttribute(USER_ID));
    }

    @Test
    public void loginWithUserEmailSucces() throws Exception {
        final MvcResult result = mockMvc
                .perform(post("/session/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login_or_email\":\"email@mail.ru\"," +
                                "\"password\":\"qwerty123\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
                .andReturn()
        ;

        assertEquals(this.id, result.getRequest().getSession().getAttribute(USER_ID));
    }

    @Test
    public void loginNotAuth() throws Exception {
        mockMvc
                .perform(post("/session/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login_or_email\":\"sergey\"," +
                                "\"password\":\"wrong password\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_LOGIN_OR_PASSWORD))
        ;
    }

    @Test
    public void logout() throws Exception {
        mockMvc
                .perform(post("/session/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login_or_email\":\"sergey\"," +
                                "\"password\":\"qwerty123\"" +
                                '}'))
                .andDo(mvcResult -> {
                    mockMvc
                            .perform(post("/session/logout")
                                    .sessionAttr(USER_ID, mvcResult.getRequest().getSession().getAttribute(USER_ID)))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
                            .andExpect(jsonPath("error").value("User session deleted"))
                    ;
                })
        ;
    }

    @Test
    public void current() throws Exception {
        mockMvc
                .perform(post("/session/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login_or_email\":\"sergey\"," +
                                "\"password\":\"qwerty123\"" +
                                '}'))
                .andDo(mvcResult -> {
                    mockMvc
                            .perform(get("/session/current")
                                    .sessionAttr(USER_ID, mvcResult.getRequest().getSession().getAttribute(USER_ID)))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
                            .andExpect(jsonPath("login").value("sergey"))
                    ;
                })
        ;
    }

}
