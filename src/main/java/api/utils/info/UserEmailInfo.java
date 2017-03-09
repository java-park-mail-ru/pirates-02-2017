package api.utils.info;

import api.utils.validator.FieldValidates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vileven on 10.03.17.
 */
public class UserEmailInfo {
    private final String email;


    @JsonCreator
    UserEmailInfo(
            @JsonProperty(value = "email", required = true) String email
    ) {
        this.email = email;
    }

    @FieldValidates(validators = {"email"})
    public String getValue() { return email; }

}
