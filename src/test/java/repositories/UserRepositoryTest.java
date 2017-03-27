package repositories;

import api.Application;
import api.model.User;
import api.repository.UserRepositoryImpl;
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

/**
 * Created by Vileven on 27.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    UserRepositoryImpl userRepository;

    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PersistenceContext
    @Autowired
    protected EntityManager em;

    private void flushAndClear(){
        em.flush();
        em.clear();
    }

    private User user;

    @Before
    public void setup() {
        user = new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now());
        userRepository.deleteAll();
        assertEquals(0, em.createQuery("SELECT u FROM User u").getResultList().size());

    }

    @Test
    public void deleteAll() {
        userRepository.deleteAll();
        assertEquals(0, em.createQuery("SELECT u FROM User u").getResultList().size());
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
    public void findUserByLoginOrByEmail() {
        user = userRepository.save(user);
        User foundUser = userRepository.findUsersByLoginOrByEmail("sergey", "");
        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        foundUser = userRepository.findUsersByLoginOrByEmail("", "email@mail.ru");
        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        foundUser = userRepository.findUsersByLoginOrByEmail("sergey", "email@mail.ru");
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    public void updateEmail() {
        user = userRepository.save(user);

        int num = userRepository.updateEmail(user.getId(), "email@yandex.ru", LocalDateTime.now());
        assertEquals(1, num);

        final User updatedUser = userRepository.find(user.getId());
        assertNotEquals(user, updatedUser);

        assertEquals("email@yandex.ru", updatedUser.getEmail());
    }
}
