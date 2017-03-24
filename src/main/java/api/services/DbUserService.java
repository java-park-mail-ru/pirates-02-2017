package api.services;

import api.model.User;
import api.repository.UserRepository;
import api.services.generic.AbstractService;
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
public class DbUserService extends AbstractService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DbUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(@NotNull UserCreationInfo userData) {
        User newUser = new User(userData.getLogin(), userData.getEmail(),
                passwordEncoder.encode(userData.getPassword()), LocalDateTime.now(), LocalDateTime.now());

        newUser = userRepository.save(newUser);
        log.info("User created: {}", newUser.toString());
    }

    @Override
    public void changeEmail(@NotNull Long id, @NotNull String email) {
        final User updatedUser = userRepository.updateEmail(id, email, LocalDateTime.now());
        log.info("User updated email: {}", updatedUser.toString());
    }

    @Override
    public void changeLogin(@NotNull Long id, @NotNull String login) {
        final User updatedUser = userRepository.updateLogin(id, login, LocalDateTime.now());
        log.info("User updated login: {}", updatedUser.toString());
    }

    @Override
    public void changePassword(@NotNull Long id, @NotNull String password) {
        final User updatedUser = userRepository.updatePassword(id, passwordEncoder.encode(password),
                LocalDateTime.now());

        log.info("User updated password: {}", updatedUser.toString());
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
        if (user.getPassword().equals(passwordEncoder.encode(password))) {
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

    @Override
    protected void setTestEnvironment() {

    }

    @Override
    protected void setProductionEnvironment() {

    }
}
