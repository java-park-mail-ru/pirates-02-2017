package api.utils.validator;

import org.jetbrains.annotations.NotNull;

/**
 * Created by coon on 08.03.17.
 */


public interface Validator {

    /**
     * Валидация данных
     * @param value строка для валидации
     * @return Iterable<ValidatorMessage> в любом случае.
     */
    @NotNull
    public Iterable<ValidatorMessage> validate(@NotNull String value);

}
