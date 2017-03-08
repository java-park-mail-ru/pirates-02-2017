package api.validators;

import api.utils.validator.Validator;
import api.utils.validator.ValidatorMessage;
import api.utils.validator.ValidatorStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//import org.springframework.context.annotation.Bean;

/**
 * Created by coon on 08.03.17.
 */

public class PasswordValidator extends Validator {

    @Override
    @NotNull
    public Iterable<ValidatorMessage> validate(@NotNull String value) {
        List<ValidatorMessage> messages = new ArrayList<>();

        if (value.length() < 3 || value.length() > 30) {
            messages.add(
                    new ValidatorMessage(ValidatorStatus.ERROR,
                    "Password must be between 3 and 30 symbols"
                    ));
        } else {
            messages.add(
                    new ValidatorMessage(ValidatorStatus.OK,
                            "Your password is great!"
                    ));
        }

        return messages;
    }

}
