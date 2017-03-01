package api.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * дисиреализация информации о пользователе из Json
 */
public final class GetUserInfo {
    private final String login;
    private final String email;
    private final String password;

    @JsonCreator
    GetUserInfo(@JsonProperty("login") String login, @JsonProperty("email") String email,
                @JsonProperty("password") String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
