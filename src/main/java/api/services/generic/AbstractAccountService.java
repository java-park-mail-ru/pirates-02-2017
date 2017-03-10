package api.services.generic;

import api.model.User;
import api.utils.info.UserCreationInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;


@Service
public abstract class AbstractAccountService {

    private boolean isTest = false;


    protected abstract void setTestEnvironment();
    protected abstract void setProductionEnvironment();


    public final void setTest(final boolean value) {
        this.isTest = value;
    }

    public final boolean isTest() {
        return this.isTest;
    }


    public abstract void drop();
    public abstract void createUser(@NotNull UserCreationInfo userData);
    public abstract void changeEmail(@NotNull Long id,@NotNull String email);
    public abstract void changeLogin(@NotNull Long id,@NotNull String login);
    public abstract void changePassword(@NotNull Long id,@NotNull String password);
    public abstract boolean deleteUserbyId(@NotNull Long id);
    public abstract User authenticateUser(@NotNull String value, @NotNull String password);
    public abstract User getUserByLoginOrEmail(@NotNull String value);
    public abstract User getUserByLogin(@NotNull String login);
    public abstract User getUserById(@NotNull Long id);
    public abstract boolean hasEmail(@NotNull String email);
    public abstract boolean hasLogin(@NotNull String login);
}
