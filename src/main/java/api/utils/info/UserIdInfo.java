package api.utils.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Тело запроса с id
 */
public class UserIdInfo {
    private final Long id;

    @JsonCreator
    UserIdInfo(
            @JsonProperty(value = "id", required = true) Long id
    ) {
        this.id = id;
    }

    public Long getId() { return id; }
}
