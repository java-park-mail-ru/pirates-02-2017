package api.utils.validator;

/**
 * Created by coon on 08.03.17.
 */


import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ValidatorStatus {
    ERROR("error"),
    WARNING("warning"),
    OK("ok")
    ;


    private final String text;


    private ValidatorStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @JsonValue
    public String getValue() {
        return text;
    }
}
