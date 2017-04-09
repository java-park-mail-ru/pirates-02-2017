package repositories;


import api.Application;
import api.DAO.HibernateDAO;
import api.model.User;
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


public class HibernateDAOTest {

    @Autowired
    private HibernateDAO<User, Long> dao;

    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private User user;


    private void flushAndClear() {
        this.entityManager.flush();
        this.entityManager.clear();
    }


    @NotNull
    private String userTable() {
        return User.class.getSimpleName();
    }


    @Before
    public void setup() {
        user = new User("sergey", "email@mail.ru",
                this.encoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now());
        dao.deleteAll();

        assertEquals(0, entityManager.createQuery(
                "SELECT u FROM " + this.userTable() + " u").getResultList().size());
    }


    @Test
    public void deleteAll() {
        dao.deleteAll();
        assertEquals(0, entityManager.createQuery(
                "SELECT u FROM " + this.userTable() + " u").getResultList().size());
    }


    @Test
    public void persist() {
        user = dao.persist(user);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("sergey", user.getLogin());
    }


    @Test
    public void find() {
        user = dao.persist(user);
        final User foundUser = dao.find(user.getId());

        assertEquals(user, foundUser);
    }


    @Test
    public void delete() {
        user = dao.persist(user);
        dao.delete(user.getId());
        final User foundUser = dao.find(user.getId());

        assertNull(foundUser);
    }

}
