package api.utils.validator.generic;

import api.utils.validator.ValidatorMessage;
import api.utils.validator.ValidatorStatus;
import org.jetbrains.annotations.NotNull;



public abstract class Validator {

    /**
     * Валидация данных
     * @param value строка для валидации
     * @return Iterable<ValidatorMessage> в любом случае.
     */
    @NotNull
    public abstract Iterable<ValidatorMessage> validate(@NotNull String value);


    public boolean isValid(@NotNull String value, boolean strict) {
        final Iterable<ValidatorMessage> messages = this.validate(value);

        for (ValidatorMessage message: messages) {
            if ((message.status == ValidatorStatus.ERROR) ||
                    (strict && message.status == ValidatorStatus.WARNING)) {
                return false;
            }
        }

        return true;
    }

}
