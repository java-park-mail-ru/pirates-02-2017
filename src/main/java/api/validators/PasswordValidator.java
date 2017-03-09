package api.validators;

import api.utils.validator.Validator;
import api.utils.validator.ValidatorMessage;
import api.utils.validator.ValidatorStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//import org.springframework.context.annotation.Bean;

/**
 * Created by coon on 08.03.17.
 */

public class PasswordValidator extends Validator {

    Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");

    @Override
    @NotNull
    public Iterable<ValidatorMessage> validate(@NotNull String value) {
        List<ValidatorMessage> messages = new ArrayList<>();
        final int min = 3;
        final int max = 30;

        if (value.length() < min || value.length() > max) {
            messages.add(
                    new ValidatorMessage(ValidatorStatus.ERROR,
                    "Пароль должен быть от 3 до 30 символов длиной."
                    ));
            return messages;
        };

        //if (value.co)

            messages.add(
                    new ValidatorMessage(ValidatorStatus.OK,
                            "Хороший пароль. <strong>Вы замечательны!</strong>"
                    ));
        //}

        return messages;

    }

}
