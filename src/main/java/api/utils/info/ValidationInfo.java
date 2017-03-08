package api.utils.info;

/**
 * Created by coon on 08.03.17.
 */


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ValidationInfo {
    private final String value;

    @JsonCreator
    ValidationInfo(
            @JsonProperty(value = "value", required = true) String value
    ) {
        this.value = value;
    }

    public String getValue() { return value; }
}
