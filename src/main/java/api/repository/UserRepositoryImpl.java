package api.repository;

import api.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Vileven on 27.03.17.
 */
public class UserRepositoryImpl extends AbstractBaseDAO<User, Long> implements UserRepository {

    @Override
    public User findUserByLogin(String login) {
        return findByQueryWithParams("SELECT u FROM User u WHERE lower(u.login) = lower(:login)",
                Collections.singletonMap("login", login)).get(0);
    }

    @Override
    public User findOne(Long id) {
        return findByQueryWithParams("SELECT u FROM User u WHERE i.id = :id",
                Collections.singletonMap("id", id)).get(0);
    }

    @Override
    public int updateEmail(Long id, String email, LocalDateTime now) {
        //чет тут я понял, что возможно стоило не именнованные параметры делать, а просто порядковые... Можно переписать,
        // можно оставить
        Map<String, Object> params = Collections.singletonMap("id", id);
        params.put("email", email);
        params.put("now", now);
        return updateByQueryWithParams("UPDATE User u SET u.email = :email, u.updatedAt = :now WHERE u.id = :id",
                params);
    }

    @Override
    public int updateLogin(Long id, String login, LocalDateTime now) {
        return 0;
    }

    @Override
    public int updatePassword(Long id, String password, LocalDateTime now) {
        return 0;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByLoginOrEmail(String loginOrEmail) {
        return null;
    }

    @Override
    public User findUsersByLoginOrByEmail(String login, String email) {
        return null;
    }

    @Override
    public User save(@NotNull User user) {
        return null;
    }
}
