package api.repository;


import api.model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;


public interface IUserRepository<T extends Model<ID>, ID extends Serializable> {

    int updateEmail(@NotNull ID id, @NotNull String email, @NotNull LocalDateTime now);

    int updateLogin(@NotNull ID id, @NotNull  String login, @NotNull LocalDateTime now);

    int updatePassword(@NotNull ID id, @NotNull String password,@NotNull LocalDateTime now);

    @Nullable
    T findUserByEmail(@NotNull String email);

    @Nullable
    T findUserByLogin(@NotNull String login);

    @Nullable
    T findOne(@NotNull Long id);

    @Nullable
    T findUserByLoginOrEmail(@NotNull String loginOrEmail);

    // @Nullable
    // T findUsersByLoginOrByEmail(@NotNull String login, @NotNull String email);

}
