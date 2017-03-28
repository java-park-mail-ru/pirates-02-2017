package api.services;

import api.model.User;
import api.repository_old.UserRepository;
import api.services.generic.UserService;
import api.utils.info.UserCreationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Vileven on 24.03.17.
 */
@Service
public class DbUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DbUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Nullable
    public User createUser(@NotNull UserCreationInfo userData) {
        final User newUser = new User(userData.getLogin(), userData.getEmail(),
                passwordEncoder.encode(userData.getPassword()), LocalDateTime.now(), LocalDateTime.now());

        final User createdUser = userRepository.save(newUser);
        log.info("User created: {}", newUser.toString());
        return createdUser;
    }

    @Override
    public void changeEmail(@NotNull Long id, @NotNull String email) {
        final int res = userRepository.updateEmail(id, email, LocalDateTime.now());
        if (res > 0) {
            log.info("User updated email: {}", email);
        }
    }

    @Override
    public void changeLogin(@NotNull Long id, @NotNull String login) {
        final int res = userRepository.updateLogin(id, login, LocalDateTime.now());
        if (res > 0) {
            log.info("User updated login: {}", login);
        }
    }

    @Override
    public void changePassword(@NotNull Long id, @NotNull String password) {
        final int res = userRepository.updatePassword(id, passwordEncoder.encode(password),
                LocalDateTime.now());
        if (res > 0) {
            log.info("User updated password: {}", password);
        }
    }

    @Override
    public void deleteUserbyId(@NotNull Long id) {
        userRepository.delete(id);
        log.info("Delete user with id {}", id);
    }

    @Nullable
    @Override
    public User authenticateUser(@NotNull String value, @NotNull String password) {
        final User user = userRepository.findUserByLoginOrEmail(value);
        if (user == null) {
            return null;
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            log.info("User authenicated: {}", user.toString());
            return user;
        }
        return null;
    }

    @Nullable
    @Override
    public User getUserByLoginOrEmail(@NotNull String value) {
        return userRepository.findUserByLoginOrEmail(value);
    }

    @Override
    public User getUserByLoginOrByEmail(@NotNull String login, @NotNull String email) {
        return userRepository.findUsersByLoginOrByEmail(login, email);
    }

    @Nullable
    @Override
    public User getUserByLogin(@NotNull String login) {
        return userRepository.findUserByLogin(login);
    }

    @Nullable
    @Override
    public User getUserById(@NotNull Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public boolean hasEmail(@NotNull String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    @Override
    public boolean hasLogin(@NotNull String login) {
        return userRepository.findUserByLogin(login) != null;
    }


//    @Override
//    public void drop() {
//        userRepository.deleteAll();
//    }

}
