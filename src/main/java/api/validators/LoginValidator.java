package api.validators;


import api.utils.validator.Validator;
import api.utils.validator.ValidatorMessage;
import api.utils.validator.ValidatorStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class LoginValidator extends Validator {

    @NotNull
    @Override
    public Iterable<ValidatorMessage> validate(@NotNull String value) {
        ArrayList<ValidatorMessage> messages = new ArrayList<>();

        if (!value.equalsIgnoreCase("kek")) {
            messages.add(new ValidatorMessage(ValidatorStatus.OK));
            return messages;
        }

        messages.add(new ValidatorMessage(ValidatorStatus.ERROR, "Fuck off im just a test"));
        return messages;
    }
}
