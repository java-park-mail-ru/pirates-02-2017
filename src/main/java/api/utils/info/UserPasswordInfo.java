package api.utils.info;

import api.utils.validator.FieldValidates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPasswordInfo {
    private final String password;


    @JsonCreator
    UserPasswordInfo(
            @JsonProperty(value = "password", required = true) String password
    ) {
        this.password = password;
    }

    @FieldValidates(validators = {"password"})
    public String getValue() { return password; }
}
