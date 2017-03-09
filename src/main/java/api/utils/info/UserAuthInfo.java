package api.utils.info;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAuthInfo {
    private final String loginOrEmail;
    private final String password;
    private final Boolean vk;

    @JsonCreator
    UserAuthInfo(
            @JsonProperty(value = "login_or_email", required = true) String loginOrEmail,
            @JsonProperty(value = "password", required = true) String password,
            @JsonProperty(value = "password", required = false)Boolean vk) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
        this.vk = vk;
    }

    public String getLoginOrEmail() { return loginOrEmail; }
    public String getPassword() { return password; }

    public Boolean getVk() {
        return vk;
    }
}
