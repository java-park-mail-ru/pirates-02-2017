package api.validators;


import api.services.AccountService;
import api.utils.validator.generic.Validator;
import api.utils.validator.ValidatorMessage;
import api.utils.validator.ValidatorStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class EmailValidator extends Validator {

    private final AccountService accountService;

    public EmailValidator(final AccountService accountService) {
        this.accountService = accountService;
    }


    @NotNull
    @Override
    public Iterable<ValidatorMessage> validate(@NotNull String value) {
        final ArrayList<ValidatorMessage> messages = new ArrayList<>();

        if ((value.length() < 3) || (value.length() > 30)) {
            messages.add(new ValidatorMessage(ValidatorStatus.ERROR,
                    "Email должен быть от 3 до 30 символов длиной."));
            return messages;
        }

        if (!value.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]+\\.[a-zA-Z0-9_]+$")) {
            messages.add(new ValidatorMessage(ValidatorStatus.ERROR, "Некорректный email!"));
            return messages;
        }

        if (accountService.hasEmail(value)) {
            messages.add(new ValidatorMessage(ValidatorStatus.ERROR, "Email " + value +
                    " уже используется."));
            return messages;
        }

        messages.add(new ValidatorMessage(ValidatorStatus.OK));
        return messages;
    }
}
