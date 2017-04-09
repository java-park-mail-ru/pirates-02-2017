package api.validators;

import api.utils.validator.generic.Validator;
import api.utils.validator.ValidatorMessage;
import api.utils.validator.ValidatorStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class PasswordValidator extends Validator {

    @Override
    @NotNull
    public Iterable<ValidatorMessage> validate(@NotNull String value) {
        final List<ValidatorMessage> messages = new ArrayList<>();

        if (value.length() < 3 || value.length() > 30) {
            messages.add(new ValidatorMessage(ValidatorStatus.ERROR,
                    "Пароль должен быть от 3 до 30 символов длиной."));
            return messages;
        }

        if (value.length() < 8) {
            messages.add(new ValidatorMessage(ValidatorStatus.WARNING,
                    "Ваш пароль слишком короткий, рекомендуемая длина пароля &ndash; 8 символов."));
        }

        if (value.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
            if (messages.isEmpty()) {
                messages.add(new ValidatorMessage(ValidatorStatus.OK,
                        "Хороший пароль. <strong>Вы восхитительны!</strong>"));
            }
        } else {
            messages.add(new ValidatorMessage(ValidatorStatus.WARNING,
                    "Ваш пароль слишком слабый. Желательно, чтобы пароль содержал как " +
                            "<strong>минимум</strong> одну строчную букву, одну заглавную и одну цифру."));
        }

        return messages;
    }

}
