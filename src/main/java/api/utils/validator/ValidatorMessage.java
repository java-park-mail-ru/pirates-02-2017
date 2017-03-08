package api.utils.validator;

import org.jetbrains.annotations.Nullable;

/**
 * Created by coon on 08.03.17.
 */


public class ValidatorMessage {

    public final String msg;
    public final ValidatorStatus status;


    /**
     * @param status результат валидаци (ошибка, предупреждение или успех)
     * @param msg Текстовый комментарий (может отображаться в пользовательском интерфейсе)
     */
    public ValidatorMessage(ValidatorStatus status, @Nullable String msg) {
        this.msg = msg;
        this.status = status;
    }


    /**
     * @param status результат валидаци (ошибка, предупреждение или успех)
     */
    public ValidatorMessage(ValidatorStatus status) {
        this(status, null);
    }

}
