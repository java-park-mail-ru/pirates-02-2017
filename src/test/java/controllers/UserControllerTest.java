package controllers;

import api.Application;
import api.model.User;
import api.repository_old.UserRepository;
import api.utils.ErrorCodes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static api.controllers.SessionController.USER_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Vileven on 26.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class UserControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private Long id;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        userRepository.deleteAll();
        final User user = userRepository.save(new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now()));
        id = user.getId();
    }

    @Test
    public void getByIdSucces() throws Exception {
        mockMvc
                .perform(post("/user/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\":\"%d\"}", this.id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value("sergey"))
                .andExpect(jsonPath("email").value("email@mail.ru"))
        ;
    }

    @Test
    public void getByIdUserNotFound() throws Exception {
        mockMvc
                .perform(post("/user/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\":\"%d\"}", this.id + 1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.USER_NOT_FOUND));
        ;
    }

    @Test
    public void createUserSucces() throws Exception {
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login\":\"vi2le2131ven\"," +
                                "\"email\":\"em221ail@mail.ru\"," +
                                "\"password\":\"3213124cdsdfsfgd\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
        ;
    }

    @Test
    public void createUserExistsLogin() throws Exception {
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login\":\"sergey\"," +
                                "\"email\":\"em221ail@mail.ru\"," +
                                "\"password\":\"3213124cdsdfsfgd\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
                .andExpect(jsonPath("error").value("Bad validator"))
        ;
    }

    @Test
    public void createUserExistsEmail() throws Exception {
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login\":\"vi2le2131ven\"," +
                                "\"email\":\"email@mail.ru\"," +
                                "\"password\":\"3213124cdsdfsfgd\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
                .andExpect(jsonPath("error").value("Bad validator"))
        ;
    }

    @Test
    public void createUserBadLogin() throws Exception {
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login\":\"23\"," +
                                "\"email\":\"email11@mail.ru\"," +
                                "\"password\":\"3213124cdsdfsfgd\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
                .andExpect(jsonPath("error").value("Bad validator"))
        ;
    }

    @Test
    public void createUserBadEmail() throws Exception {
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login\":\"vileven123\"," +
                                "\"email\":\"email11mail.ru\"," +
                                "\"password\":\"3213124cdsdfsfgd\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
                .andExpect(jsonPath("error").value("Bad validator"))
        ;
    }

    @Test
    public void createUserBadPassword() throws Exception {
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{' +
                                "\"login\":\"vileven123\"," +
                                "\"email\":\"email11@mail.ru\"," +
                                "\"password\":\"s2\"" +
                                '}'))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
                .andExpect(jsonPath("error").value("Bad validator"))
        ;
    }

    @Test
    public void changeLoginSucces() throws Exception {
        mockMvc
                .perform(post("/user/changeLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(USER_ID, this.id)
                        .content("{\"login\":\"vileven123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
        ;
    }

    @Test
    public void changeLoginSessionError() throws Exception {
        mockMvc
                .perform(post("/user/changeLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"vileven123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SESSION_INVALID))
        ;
    }

    @Test
    public void changeLoginIncorrectLogin() throws Exception {
        mockMvc
                .perform(post("/user/changeLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(USER_ID, this.id)
                        .content("{\"login\":\"12\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
        ;
    }

    @Test
    public void changeEmailSucces() throws Exception {
        mockMvc
                .perform(post("/user/changeEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(USER_ID, this.id)
                        .content("{\"email\":\"email123@mail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
        ;
    }

    @Test
    public void changeEmailSessionError() throws Exception {
        mockMvc
                .perform(post("/user/changeEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"email123@mail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SESSION_INVALID))
        ;
    }

    @Test
    public void changeEmailIncorrectEmail() throws Exception {
        mockMvc
                .perform(post("/user/changeEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(USER_ID, this.id)
                        .content("{\"email\":\"12\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
        ;
    }

    @Test
    public void changePasswordSucces() throws Exception {
        mockMvc
                .perform(post("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(USER_ID, this.id)
                        .content("{\"password\":\"qwerty123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
        ;
    }

    @Test
    public void changePasswordSessionError() throws Exception {
        mockMvc
                .perform(post("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"email123@mail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SESSION_INVALID))
        ;
    }

    @Test
    public void changePasswordIncorrectPassword() throws Exception {
        mockMvc
                .perform(post("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(USER_ID, this.id)
                        .content("{\"password\":\"12\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.BAD_VALIDATOR))
        ;
    }

    @Test
    public void deleteUserSucces() throws Exception {
        mockMvc
                .perform(post("/user/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(USER_ID, this.id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SUCCESS))
        ;
    }

    @Test
    public void deleteUserWrongSession() throws Exception {
        mockMvc
                .perform(post("/user/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ErrorCodes.SESSION_INVALID))
        ;
    }
}
