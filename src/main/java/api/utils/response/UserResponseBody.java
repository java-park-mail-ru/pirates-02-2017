package api.utils.response;

import java.time.LocalDateTime;

/**
 * Тело ответа User
 */
public class UserResponseBody extends LoginResponseBody {

    public final String email;
    public final LocalDateTime createdAt;
    public final LocalDateTime updatedAt;


    public UserResponseBody(int status, String login, String email,
                            LocalDateTime createdAt, LocalDateTime updatedAt,  String msg) {
        super(status, msg, login);
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
