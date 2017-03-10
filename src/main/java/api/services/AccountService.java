package api.services;

import api.model.User;
import api.services.generic.AbstractAccountService;
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
public class AccountService extends AbstractAccountService {

    private final PasswordEncoder encoder;

    private Map<Long, User> idToUser = null;
    private AtomicLong counter = null;

    private final Map<Long, User> testIdToUser = new ConcurrentHashMap<>();
    private final AtomicLong testCounter = new AtomicLong();

    private final Map<Long, User> productionIdToUser = new ConcurrentHashMap<>();
    private final AtomicLong productionCounter = new AtomicLong();


    AccountService (PasswordEncoder encoder) {
        this.encoder = encoder;
        setProductionEnvironment();
    }


    @Override
    protected void setTestEnvironment() {
        this.idToUser = this.testIdToUser;
        this.counter = this.testCounter;
    }

    @Override
    protected void setProductionEnvironment() {
        this.idToUser = this.productionIdToUser;
        this.counter = productionCounter;
    }


    /**
     * Создание пользователя
     * @param userData объект UserCreationInfo тела запроса
     */
    public void createUser(@NotNull UserCreationInfo userData) {
        final String encodedPassword = encoder.encode(userData.getPassword());
        final User newUser = new User(counter.incrementAndGet(),
                    userData.getLogin(), userData.getEmail(), encodedPassword, LocalDateTime.now(), LocalDateTime.now());
            idToUser.put(newUser.getId(), newUser);
    }

    /**
     * Изменить email пользователя
     * @param id идентификатор
     * @param email почта
     */
    public void changeEmail(@NotNull Long id,@NotNull String email) {
        final User user = idToUser.get(id);
        final User changedUser = new User(id, user.getLogin(), email, user.getPassword(),
                user.getCreatedAt(), LocalDateTime.now());

        idToUser.replace(id, changedUser);
    }

    /**
     * Изменить логин пользователя
     * @param id идентификатор пользователя
     * @param login новый логин
     */
    public void changeLogin(@NotNull Long id,@NotNull String login) {
        final User user = idToUser.get(id);
        final User changedUser = new User(id, login, user.getEmail(), user.getPassword(),
                user.getCreatedAt(), LocalDateTime.now());

        idToUser.replace(id, changedUser);
    }

    /**
     * Изменить пароль
     * @param id идентификатор пользователя
     * @param password новый пароль
     */
    public void changePassword(@NotNull Long id,@NotNull String password) {
        final User user = idToUser.get(id);
        final User changedUser = new User(id, user.getLogin(), user.getEmail(), encoder.encode(password),
                user.getCreatedAt(), LocalDateTime.now());

        idToUser.replace(id, changedUser);
    }

    /**
     * Удалить пользователя по id
     * @param id идентификатор пользователя
     * @return true если опреация прошла успешна, иначе false
     */
    public boolean deleteUserbyId(@NotNull Long id) {
        final User user = idToUser.get(id);
        if (user != null) {
            idToUser.remove(id);
            return true;
        }
        return false;
    }


    /**
     * Аутентификация пользователя
     * @param value логин или email
     * @param password пароль
     * @return объект <code>User</code> если операция прошла успешно, иначе null
     */
    @Nullable
    public User authenticateUser(@NotNull String value, @NotNull String password) {
        final User user = getUserByLoginOrEmail(value);
        if (user != null) {
            if (encoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Найти пользователя по логину или email
     * @param value логин или email
     * @return Объект User если пользователь существует, иначе null
     */
    @Nullable
    public User getUserByLoginOrEmail(@NotNull String value) {
        for (User user : idToUser.values()) {
            if (value.equals(user.getLogin()) || value.equals(user.getEmail())) {
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
        for (User user: idToUser.values()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
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

    /**
     * Проверить существует ли пользователь, login уникален
     * @param email емайл пользователя
     * @return true если пользователь с таким емайлом существует, иначе false
     */
    public boolean hasEmail(@NotNull String email){
        for (User user : idToUser.values()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверить существует ли пользователь, login уникален
     * @param login логин пользователя
     * @return true если пользователь с таким логином существует, иначе false
     */
    public boolean hasLogin(@NotNull String login){
        for (User user : idToUser.values()) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }


    public void drop() {
        this.idToUser.clear();
        this.counter.set(0);
    }
}
