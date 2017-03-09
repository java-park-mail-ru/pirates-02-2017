package api.utils.info;


import api.utils.validator.FieldValidates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAuthInfo {
    private final String loginOrEmail;
    private final String password;

    @JsonCreator
    UserAuthInfo(
            @JsonProperty(value = "login_or_email", required = true) String loginOrEmail,
            @JsonProperty(value = "password", required = true) String password) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
    }

    public String getLoginOrEmail() { return loginOrEmail; }

    public String getPassword() { return password; }

}
