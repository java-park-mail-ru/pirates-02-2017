package repositories;

import api.Application;
import api.model.User;
import api.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PersistenceContext
    @Autowired
    private EntityManager em;

    private void flushAndClear(){
        em.flush();
        em.clear();
    }

    private User user;


    @NotNull
    private String userTable() {
        return User.class.getSimpleName();
    }


    @Before
    public void setup() {
        user = new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now());
        userRepository.deleteAll();

        assertEquals(0, em.createQuery(
                "SELECT u FROM " + this.userTable() + " u").getResultList().size());
    }


    @Test
    public void deleteAll() {
        userRepository.deleteAll();
        assertEquals(0, em.createQuery(
                "SELECT u FROM " + this.userTable() + " u").getResultList().size());
    }


    @Test
    public void save() {
        user = userRepository.save(user);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("sergey", user.getLogin());
    }


    @Test
    public void findUserById() {
        user = userRepository.save(user);
        final User foundUser = userRepository.findOne(user.getId());
        assertEquals(user, foundUser);
    }


    @Test
    public void findUserByLogin() {
        user = userRepository.save(user);
        final User foundUser = userRepository.findUserByLogin("sergey");
        assertNotNull(foundUser);
        assertEquals("sergey", foundUser.getLogin());
        assertEquals(user, foundUser);
    }


    @Test
    public void findUserByEmail() {
        user = userRepository.save(user);
        final User foundUser = userRepository.findUserByEmail("email@mail.ru");
        assertNotNull(foundUser);
        assertEquals("email@mail.ru", foundUser.getEmail());
        assertEquals(user, foundUser);
    }


    @Test
    public void findUserByLoginOrEmail() {
        user = userRepository.save(user);
        User foundUser = userRepository.findUserByLoginOrEmail("sergey");
        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        foundUser = userRepository.findUserByLoginOrEmail("email@mail.ru");
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }


    @Test
    public void updateEmail() {
        user = userRepository.save(user);

        final int num = userRepository.updateEmail(user.getId(), "email@yandex.ru");
        assertEquals(1, num);

        final User updatedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getEmail(), updatedUser.getEmail());

        assertEquals("email@yandex.ru", updatedUser.getEmail());
    }


    @Test
    public void updatePassword() {
        user = userRepository.save(user);

        final int num = userRepository.updatePassword(
                user.getId(), passwordEncoder.encode("123qwerty"));
        assertEquals(1, num);

        final User updatedUser = userRepository.findOne(user.getId());
        assertTrue(passwordEncoder.matches("123qwerty", updatedUser.getPassword()));
    }


    @Test
    public void updateLogin() {
        user = userRepository.save(user);

        final int num = userRepository.updateLogin(user.getId(), "vileven");
        assertEquals(1, num);

        final User updatedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getLogin(), updatedUser.getLogin());

        assertEquals("vileven", updatedUser.getLogin());
    }


    @Test
    public void deleteOne() {
        user = userRepository.save(user);

        userRepository.delete(user.getId());

        final User foundUser = userRepository.findOne(user.getId());
        assertNull(foundUser);
    }

}
