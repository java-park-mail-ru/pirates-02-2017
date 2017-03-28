package api.utils.info;

import api.utils.validator.FieldValidates;
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

    @FieldValidates(validators = {"login"})
    public String getValue() { return login; }

}
