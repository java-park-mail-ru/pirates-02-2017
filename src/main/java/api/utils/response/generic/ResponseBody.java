package api.utils.response.generic;

/**
 * Тело ответа
 */
public class ResponseBody {
    public final int status;
    public final String error;


    public ResponseBody(int status, String msg) {
        this.status = status;
        this.error = msg;
    }
}
