package api.utils;

/**
 * Тело запроса с полем Логина
 */
public class LoginResponseBody extends ResponseBody {
    public final String login;

    public LoginResponseBody(int status, String login, String msg) {
        super(status, msg);
        this.login = login;
    }
}
