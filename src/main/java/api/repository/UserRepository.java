package api.repository;

import api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * На основе этого интерфейса Spring Data предоставит реализации с методов для работы с БД
 */

//@SuppressWarnings("InterfaceNeverImplemented")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("UPDATE User u SET u.email = :email, u.updatedAt = :now WHERE u.id = :id")
    User updateEmail(@Param("id")Long id, @Param("email") String email, @Param("now")LocalDateTime now);

    @Modifying
    @Query("UPDATE User u SET u.login = :login, u.updatedAt = :now WHERE u.id = :id")
    User updateLogin(@Param("id")Long id, @Param("login") String login, @Param("now")LocalDateTime now);

    @Modifying
    @Query("UPDATE User u SET u.password = :password, u.updatedAt = :now WHERE u.id = :id")
    User updatePassword(@Param("id")Long id, @Param("password") String password, @Param("now")LocalDateTime now);

    @Query("SELECT u FROM User u WHERE lower(u.login) = lower(:login)")
    User findUserByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE lower(u.email) = lower(:email)")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE lower(u.login) = lower(:login_or_email) OR lower(u.email) = lower(:login_or_email)")
    User findUserByLoginOrEmail(@Param("login_or_email") String loginOrEmail);
}
