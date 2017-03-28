package api.utils.response;


/**
 * Тело ответа User
 */
public class UserResponseBody extends LoginResponseBody {

    public final String email;
    public final Long createdAt;
    public final Long updatedAt;


    public UserResponseBody(int status, String login, String email,
                            Long createdAt, Long updatedAt,  String msg) {
        super(status, login, msg);
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
