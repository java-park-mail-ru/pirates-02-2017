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

    public void flushAndClear(){
        em.flush();
        em.clear();
    }

    private User user;

    @Before
    public void setup() {
        user = new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now());
        userRepository.deleteAll();
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
}
