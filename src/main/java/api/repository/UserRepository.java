package api.repository;

import api.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Интерфейс предоставляющий работу с Enitity
 */

//@SuppressWarnings("InterfaceNeverImplemented")
//@Repository
//@EntityScan(value = User)
@Transactional
public interface UserRepository extends BaseDAO<User, Long> {
    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }

    int updateEmail(@NotNull Long id, @NotNull String email, @NotNull LocalDateTime now);

    int updateLogin(@NotNull Long id, @NotNull  String login, @NotNull LocalDateTime now);

    int updatePassword(@NotNull Long id, @NotNull String password,@NotNull LocalDateTime now);

    @Nullable
    User findUserByEmail(@NotNull  String email);

    @Nullable
    User findUserByLogin(@NotNull String login);

    @Nullable
    User findOne(@NotNull Long id);

    @Nullable
    User findUserByLoginOrEmail(@NotNull  String loginOrEmail);

    @Nullable
    User findUsersByLoginOrByEmail(@NotNull  String login, @NotNull  String email);

    @Nullable
    User save(@NotNull User user);
}
