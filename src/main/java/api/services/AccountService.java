package api.services;

import api.model.User;
import api.repository.UserRepository;
import api.utils.info.UserCreationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AccountService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final UserRepository userRepository;
    protected final PasswordEncoder encoder;


    AccountService (UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    @Nullable
    public User createUser(@NotNull UserCreationInfo userData) {
        final User newUser = new User(userData.getLogin(), userData.getEmail(),
                this.encoder.encode(userData.getPassword()), LocalDateTime.now(), LocalDateTime.now());

        final User createdUser = (User) userRepository.save(newUser);
        log.info("User created: {}", newUser.toString());

        return createdUser;
    }


    public void changeEmail(long id, @NotNull String email) {
        final int res = userRepository.updateEmail(id, email);

        if (res > 0) {
            log.info("User updated email: {}", email);
        }
    }


    public void changeLogin(long id, @NotNull String login) {
        final int res = userRepository.updateLogin(id, login);

        if (res > 0) {
            log.info("User updated login: {}", login);
        }
    }


    public void changePassword(long id, @NotNull String password) {
        final int res = userRepository.updatePassword(id, encoder.encode(password));

        if (res > 0) {
            log.info("User updated password: {}", password);
        }
    }


    public void deleteUserById(long id) {
        userRepository.delete(id);
        log.info("Delete user with id {}", id);
    }


    @Nullable
    public User authenticateUser(@NotNull String value, @NotNull String password) {
        final User user = userRepository.findUserByLoginOrEmail(value);

        if (user == null) {
            return null;
        }

        if (encoder.matches(password, user.getPassword())) {
            log.info("User authenicated: {}", user.toString());
            return user;
        }

        return null;
    }


    @Nullable
    public User getUserByLoginOrEmail(@NotNull String value) {
        return userRepository.findUserByLoginOrEmail(value);
    }


    @Nullable
    public User getUserByLogin(@NotNull String login) {
        return userRepository.findUserByLogin(login);
    }


    @Nullable
    public User getUserById(long id) {
        return userRepository.findOne(id);
    }


    public boolean hasEmail(@NotNull String email) {
        return userRepository.findUserByEmail(email) != null;
    }


    public boolean hasLogin(@NotNull String login) {
        return userRepository.findUserByLogin(login) != null;
    }

}
