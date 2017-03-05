package api.services;

import api.model.User;
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

    private PasswordEncoder encoder;
    private final Map<String, User> loginToUser = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    AccountService (PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    public boolean createUser(@NotNull String login, @NotNull String email, @NotNull String password) {
        if (!loginToUser.containsKey(login)) {
            final String encodedPassword = encoder.encode(password);
            final User newUser = new User(counter.incrementAndGet(),
                    login, email, encodedPassword, LocalDateTime.now(), LocalDateTime.now());

            loginToUser.put(login, newUser);
            return true;
        }

        return false;
    }


    public boolean deleteUser(@NotNull String login) {
        return loginToUser.remove(login) != null;
    }


    @Nullable
    public User authenticateUser(@NotNull String login, @NotNull String password) {
        if (loginToUser.containsKey(login)) {
            final User user = loginToUser.get(login);
            if (user != null && encoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }


    public User getUserByLogin(@NotNull String login) {
        return loginToUser.get(login);
    }

}
