package api.utils;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UserLoginInfo {
    private final String login;

    @JsonCreator
    UserLoginInfo(
            @JsonProperty(value = "login", required = true) String login
    ) {
        this.login = login;
    }

    public String getLogin() { return login; }
}
