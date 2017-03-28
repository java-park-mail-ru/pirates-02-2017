package api.repository;


import api.DAO.HibernateDAO;
import api.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HibernateUserRepository extends HibernateDAO<User, Long> implements IUserRepository<User, Long> {

    private User firstOrNull(List<User> result) {
        if (!result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }


    @Override
    public int updateEmail(@NotNull Long id, @NotNull String email, @NotNull LocalDateTime now) {
        final Map<String, Object> params = new HashMap<>();

        params.put("id", id);
        params.put("email", email);
        params.put("now", now);

        return this.update("UPDATE User u SET u.email = :email, u.updatedAt = :now WHERE u.id = :id",
                params);
    }


    @Override
    public int updateLogin(@NotNull Long id, @NotNull String login, @NotNull LocalDateTime now) {
        final Map<String, Object> params = new HashMap<>();

        params.put("id", id);
        params.put("login", login);
        params.put("now", now);

        return this.update("UPDATE User u SET u.login = :login, u.updatedAt = :now WHERE u.id = :id",
                params);
    }


    @Override
    public int updatePassword(@NotNull Long id, @NotNull String password, @NotNull LocalDateTime now) {
        final Map<String, Object> params = new HashMap<>();

        params.put("id", id);
        params.put("password", password);
        params.put("now", now);

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
    public User findOne(@NotNull Long id) {
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

}
