package api.repository;

import api.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vileven on 27.03.17.
 */
@Repository("UserRepository")
@Transactional
public class UserRepositoryImpl extends AbstractBaseDAO<User, Long> implements UserRepository {

    @Override
    public User findUserByLogin(String login) {
         final List<User> res = findByQueryWithParams("SELECT u FROM User u WHERE lower(u.login) = lower(:login)",
                Collections.singletonMap("login", login));
         if (!res.isEmpty()) {
             return res.get(0);
         } else {
             return null;
         }
    }

    @Nullable
    @Override
    public User findOne(Long id) {
        final List<User> res = findByQueryWithParams("SELECT u FROM User u WHERE u.id = :id",
                Collections.singletonMap("id", id));
        if (!res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int updateEmail(Long id, String email, LocalDateTime now) {
        //чет тут я понял, что возможно стоило не именнованные параметры делать, а просто порядковые... Можно переписать,
        // можно оставить
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("email", email);
        params.put("now", now);
        return updateByQueryWithParams("UPDATE User u SET u.email = :email, u.updatedAt = :now WHERE u.id = :id",
                params);
    }

    @Override
    public int updateLogin(Long id, String login, LocalDateTime now) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("login", login);
        params.put("now", now);
        return updateByQueryWithParams("UPDATE User u SET u.login = :login, u.updatedAt = :now WHERE u.id = :id",
                params);
    }

    @Override
    public int updatePassword(Long id, String password, LocalDateTime now) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("password", password);
        params.put("now", now);
        return updateByQueryWithParams("UPDATE User u SET u.password = :password, u.updatedAt = :now WHERE u.id = :id",
                params);
    }

    @Nullable
    @Override
    public User findUserByEmail(String email) {
        List<User> res = findByQueryWithParams("SELECT u FROM User u WHERE lower(u.email) = lower(:email)",
                Collections.singletonMap("email", email));
        if (!res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public User findUserByLoginOrEmail(String loginOrEmail) {
        List<User> res =  findByQueryWithParams("SELECT u FROM User u WHERE lower(u.login) = lower(:login_or_email)" +
                        "OR lower(u.email) = lower(:login_or_email)",
                Collections.singletonMap("login_or_email", loginOrEmail));
        if (!res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User findUsersByLoginOrByEmail(String login, String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("login", login);
        params.put("email", email);
        return findByQueryWithParams("SELECT u FROM User u WHERE lower(u.login) = lower(:login) OR " +
                        "lower(u.email) = lower(:email)",
                params).get(0);
    }

    @Override
    public User save(@NotNull User user) {
        return persist(user);
    }

    @Override
    public void deleteAll() {
        getEntityManager().createQuery("DELETE FROM User u").executeUpdate();
    }
}