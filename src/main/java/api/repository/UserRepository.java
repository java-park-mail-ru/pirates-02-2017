package api.repository;

import api.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * На основе этого интерфейса Spring Data предоставит реализации с методов для работы с БД
 */

//@SuppressWarnings("InterfaceNeverImplemented")
@Repository
public interface UserRepository extends BaseDAO<User, Long> {
    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }

    int updateEmail(@Param("id")Long id, @Param("email") String email, @Param("now")LocalDateTime now);

    int updateLogin(@Param("id")Long id, @Param("login") String login, @Param("now")LocalDateTime now);

    int updatePassword(@Param("id")Long id, @Param("password") String password, @Param("now")LocalDateTime now);

    User findUserByEmail(@Param("email") String email);

    User findUserByLogin(String login);

    User findOne(Long id);

    User findUserByLoginOrEmail(@Param("login_or_email") String loginOrEmail);


    /**
     * Обрати внимание на анотацию Query, оно теперь бесполезна, но idea подсвечивает синтаксис если надо будет написать
     * запрос может помочь
     * @param login
     * @param email
     * @return
     */
    @Query("SELECT u " +
            "FROM User u " +
            "WHERE lower(u.login) = lower(:login) OR lower(u.email) = lower(:email)")
    User findUsersByLoginOrByEmail(@Param("login") String login, @Param("email") String email);


    User save(@NotNull User user);
}
