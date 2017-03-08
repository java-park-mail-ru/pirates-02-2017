package api.utils.response;

/**
 * Тело ответа
 */
public class ResponseBody {
    public final int status;
    public final String msg;


    public ResponseBody(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
