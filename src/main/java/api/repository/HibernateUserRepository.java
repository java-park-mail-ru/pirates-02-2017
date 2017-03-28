package api.repository;


import api.DAO.HibernateDAO;
import api.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("UserRepository")
@Transactional
public class HibernateUserRepository extends HibernateDAO<User, Long> implements UserRepository {

    private User firstOrNull(List<User> result) {
        if (!result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }


    @Override
    public int updateEmail(long id, @NotNull String email) {
        final Map<String, Object> params = new HashMap<>();

        params.put("id", id);
        params.put("email", email);
        params.put("now", LocalDateTime.now());

        return this.update("UPDATE User u SET u.email = :email, u.updatedAt = :now WHERE u.id = :id",
                params);
    }


    @Override
    public int updateLogin(long id, @NotNull String login) {
        final Map<String, Object> params = new HashMap<>();

        params.put("id", id);
        params.put("login", login);
        params.put("now", LocalDateTime.now());

        return this.update("UPDATE User u SET u.login = :login, u.updatedAt = :now WHERE u.id = :id",
                params);
    }


    @Override
    public int updatePassword(long id, @NotNull String password) {
        final Map<String, Object> params = new HashMap<>();

        params.put("id", id);
        params.put("password", password);
        params.put("now", LocalDateTime.now());

        return this.update("UPDATE User u SET u.password = :password, u.updatedAt = :now WHERE u.id = :id",
                params);
    }


    @Nullable
    @Override
    public User findUserByEmail(@NotNull String email) {
        final List<User> res = this.select("SELECT u FROM User u WHERE lower(u.email) = lower(:email)",
                Collections.singletonMap("email", email));

        return this.firstOrNull(res);
    }


    @Nullable
    @Override
    public User findUserByLogin(@NotNull String login) {
        final List<User> res = this.select("SELECT u FROM User u WHERE lower(u.login) = lower(:login)",
                Collections.singletonMap("login", login));

        return this.firstOrNull(res);
    }


    @Nullable
    @Override
    public User findOne(long id) {
        final List<User> res = this.select("SELECT u FROM User u WHERE u.id = :id",
                Collections.singletonMap("id", id));

        return this.firstOrNull(res);
    }


    @Nullable
    @Override
    public User findUserByLoginOrEmail(@NotNull String loginOrEmail) {
        final List<User> res = this.select("SELECT u FROM User u WHERE lower(u.login) = lower(:login_or_email)" +
                        "OR lower(u.email) = lower(:login_or_email)",
                Collections.singletonMap("login_or_email", loginOrEmail));

        return this.firstOrNull(res);
    }


    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }


    @Override
    public User save(@NotNull User user) {
        return this.persist(user);
    }


    @Override
    public void delete(long id) {
        super.delete(id);
    }

}
