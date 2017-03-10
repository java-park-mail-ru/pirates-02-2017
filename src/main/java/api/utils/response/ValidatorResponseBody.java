package api.utils.response;

import api.utils.response.generic.ResponseBody;
import api.utils.validator.ValidatorMessage;
import org.jetbrains.annotations.NotNull;

/**
 * Created by coon on 08.03.17.
 */


public class ValidatorResponseBody extends ResponseBody {

    public final Iterable<ValidatorMessage> messages;


    public ValidatorResponseBody(int status, String msg, @NotNull Iterable<ValidatorMessage> messages) {
        super(status, msg);
        this.messages = messages;
    }

}
