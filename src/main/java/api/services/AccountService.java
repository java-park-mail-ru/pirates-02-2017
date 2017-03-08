package api.services;

import api.model.User;
import api.utils.info.UserCreationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class AccountService {

    private final PasswordEncoder encoder;
    private final Map<Long, User> idToUser = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    AccountService (PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    /**
     * Изменеие данных пользователя по id (Логин тоже можно изменить)
     * @param id идентификатор пользователя
     * @param newUserData объект из тела запроса на изменение
     * @return true, если операция прошла успеша. false если нет.
     */
    public boolean changeUserById(@NotNull Long id, @NotNull UserCreationInfo newUserData) {
        if (idToUser.containsKey(id)) {
            final String login = newUserData.getLogin();
            if (!loginToUser.containsKey(login)) {
                final User user = idToUser.get(id);
                final User changedUser = new User(id, login, newUserData.getEmail(),
                        encoder.encode(newUserData.getPassword()), user.getCreatedAt(), LocalDateTime.now());

                idToUser.remove(id);
                loginToUser.remove(user.getLogin());

                idToUser.put(id, changedUser);
                loginToUser.put(login, changedUser);

                return true;
            }
        }

        return false;
    }


    /**
     * Создание пользователя
     * @param userData объект UserCreationInfo тела запроса
     * @return true если опреация прошла успешна, иначе false
     */
    public boolean createUser(@NotNull UserCreationInfo userData) {
        final String login = userData.getLogin();
        final String email = userData.getEmail();
        if (!idToUser.containsKey(login)) {
            final String encodedPassword = encoder.encode(userData.getPassword());
            final User newUser = new User(counter.incrementAndGet(),
                    login, userData.getEmail(), encodedPassword, LocalDateTime.now(), LocalDateTime.now());

            loginToUser.put(login, newUser);
            idToUser.put(newUser.getId(), newUser);
            return true;
        }

        return false;
    }


    /**
     * Удалить пользователя по логину
     * @param login логин
     * @return true если опреация прошла успешна, иначе false
     */
    public boolean deleteUserbyLogin(@NotNull String login) {
        final User user = loginToUser.get(login);
        if (user != null) {
            loginToUser.remove(login);
            idToUser.remove(user.getId());
            return true;
        }
        return false;
    }

    /**
     * Удалить пользователя по id
     * @param id идентификатор пользователя
     * @return true если опреация прошла успешна, иначе false
     */
    public boolean deleteUserbyId(@NotNull Long id) {
        final User user = idToUser.get(id);
        if (user != null) {
            loginToUser.remove(user.getLogin());
            idToUser.remove(id);
            return true;
        }
        return false;
    }


    /**
     * Аутентификация пользователя
     * @param login логин
     * @param password пароль
     * @return объект <code>User</code> если операция прошла успешно, иначе null
     */
    @Nullable
    public User authenticateUser(@NotNull String login, @NotNull String password) {
        final User user = loginToUser.get(login);
        if (user != null) {
            if (encoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }


    /**
     * Вернуть пользователя по логину
     * @param login логин
     * @return объект <code>User</code> если пользователь существует, иначе null
     */
    @Nullable
    public User getUserByLogin(@NotNull String login) {
        return loginToUser.get(login);
    }


    /**
     * Вернуть пользователя по id
     * @param id Идентификатор пользователя
     * @return объект <code>User</code> если пользователь существует, иначе null
     */
    @Nullable
    public User getUserById(@NotNull Long id) {
        return idToUser.get(id);
    }

    public boolean hasEmail(@NotNull String email){
        for (User user : idToUser.values()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLogin(@NotNull String login){
        for (User user : idToUser.values()) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверить существует ли пользователь, email и login
     * @param login
     * @param email
     * @return
     */
    public boolean isUserExists(@NotNull String login, @NotNull String email) {
        for (User user : idToUser.values()) {
            if (user.getLogin().equals(login) || user.getEmail().equals(email)) {
                return false;
            }
        }

        return true;
    }


}
