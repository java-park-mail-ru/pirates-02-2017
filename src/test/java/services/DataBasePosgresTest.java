package services;

import api.Application;
import api.model.User;
import api.repository.UserRepository;
import api.services.AccountService;
import api.utils.info.UserCreationInfo;
//import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Тесты сервиса DbUserService в реальной postgresql базе
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@SpringBootTest(classes = {TestContext.class, ServiceTestConfiguration.class})
public class DataBasePosgresTest {

    @Autowired
    AccountService accountService;

    @Autowired
    UserRepository userRepository;

//    @Autowired
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Before
    public void setup() {
        userRepository.deleteAll();
        userRepository.save(new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    public void createUser() {
        final User newUser = accountService.createUser(new UserCreationInfo("sergey1", "email1@mail.ru",
                "qwerty123"));
        assertNotEquals(null, newUser);
        assertEquals("sergey1", newUser.getLogin());
    }

    @Test
    public void changeEmail(){
        final User user = userRepository.findUserByLogin("sergey");
        accountService.changeEmail(user.getId(), "e@yandex.ru");
        final User changedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getEmail(), changedUser.getEmail());
        assertEquals("e@yandex.ru", changedUser.getEmail());
    }

    @Test
    public void changeLoginl(){
        final User user = userRepository.findUserByLogin("sergey");
        accountService.changeLogin(user.getId(), "maxim");
        final User changedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getLogin(), changedUser.getLogin());
        assertEquals("maxim", changedUser.getLogin());
    }

    @Test
    public void changePassword(){
        final User user = userRepository.findUserByLogin("sergey");
        accountService.changePassword(user.getId(), "123qwerty");
        final User changedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getPassword(), changedUser.getPassword());
        assertEquals(true,
                passwordEncoder.matches("123qwerty", changedUser.getPassword()));
    }

    @Test
    public void deleteUser() {
        User user = userRepository.findUserByLogin("sergey");
        assertNotEquals(null, user);
        accountService.deleteUserById(user.getId());
        user = userRepository.findUserByLogin("sergey");
        assertEquals(null,user);
    }

    @Test
    public void authenticateUserSuccess() {
        final User user = userRepository.findUserByLogin("sergey");

        User authUser = accountService.authenticateUser(user.getLogin(), "qwerty123");
        assertNotEquals(null, authUser);
        assertEquals(user, authUser);

        authUser = accountService.authenticateUser(user.getEmail(), "qwerty123");
        assertNotEquals(null, authUser);
        assertEquals(user, authUser);
    }

    @Test
    public void authenticateUserError() {
        final User user = userRepository.findUserByLogin("sergey");

        User authUser = accountService.authenticateUser(user.getLogin(), "wrong password");
        assertNull(authUser);

        authUser = accountService.authenticateUser("Unexpected login", "qwerty123");
        assertNull(authUser);
    }

    @Test
    public void getUserById() {
        final User user = userRepository.findUserByLogin("sergey");
        User findedUser = accountService.getUserById(user.getId());
        assertEquals(user, findedUser);

        findedUser = accountService.getUserById(user.getId() + 1);
        assertNull(findedUser);
    }

    @Test
    public void getUserByLogin() {
        final User user = userRepository.findUserByLogin("sergey");
        User findedUser = accountService.getUserByLogin("sergey");
        assertEquals(user, findedUser);

        findedUser = accountService.getUserByLogin("sergey11");
        assertNull(findedUser);
    }
}
