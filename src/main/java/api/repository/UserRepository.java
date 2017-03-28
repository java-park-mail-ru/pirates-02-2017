package api.repository;


import api.model.Model;
import api.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;


public interface UserRepository {

    int updateEmail(long id, @NotNull String email);
    int updateLogin(long id, @NotNull  String login);
    int updatePassword(long id, @NotNull String password);

    @Nullable
    User findUserByEmail(@NotNull String email);

    @Nullable
    User findUserByLogin(@NotNull String login);

    @Nullable
    User findOne(long id);

    @Nullable
    User findUserByLoginOrEmail(@NotNull String loginOrEmail);

    // @Nullable
    // T findUsersByLoginOrByEmail(@NotNull String login, @NotNull String email);

    @Nullable
    User save(@NotNull User user);

    void delete(long id);
    void deleteAll();

}
