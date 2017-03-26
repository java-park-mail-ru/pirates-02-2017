package api.services.generic;

import api.model.User;
import api.utils.info.UserCreationInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * Интерфейс для работы с бд
 */
@Service
public interface UserService {
//    void drop();
    User createUser(@NotNull UserCreationInfo userData);
    void changeEmail(@NotNull Long id,@NotNull String email);
    void changeLogin(@NotNull Long id,@NotNull String login);
    void changePassword(@NotNull Long id,@NotNull String password);
    void deleteUserbyId(@NotNull Long id);
    User authenticateUser(@NotNull String value, @NotNull String password);
    User getUserByLoginOrEmail(@NotNull String value);
    User getUserByLoginOrByEmail(@NotNull String login, @NotNull String email);
    User getUserByLogin(@NotNull String login);
    User getUserById(@NotNull Long id);
    boolean hasEmail(@NotNull String email);
    boolean hasLogin(@NotNull String login);
}
