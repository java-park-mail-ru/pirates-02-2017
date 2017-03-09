package api.utils.info;

import api.utils.validator.FieldValidates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * дисиреализация информации о пользователе из Json
 */
public final class UserCreationInfo {
    private final String login;
    private final String email;
    private final String password;

    @JsonCreator
    UserCreationInfo(
            @JsonProperty(value = "login", required = true) String login,
            @JsonProperty(value = "email", required = true) String email,
            @JsonProperty(value = "password", required = true) String password
    ) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    @FieldValidates(validators = {"login"})
    public String getLogin() { return login; }

    @FieldValidates(validators = {"email"})
    public String getEmail() { return email; }

    @FieldValidates(validators = {"password"})
    public String getPassword() { return password; }
}
