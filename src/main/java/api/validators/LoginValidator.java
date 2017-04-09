package api.validators;


import api.services.AccountService;
import api.utils.validator.generic.Validator;
import api.utils.validator.ValidatorMessage;
import api.utils.validator.ValidatorStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class LoginValidator extends Validator {

    private final AccountService accountService;

    public LoginValidator(final AccountService accountService) {
        this.accountService = accountService;
    }


    @NotNull
    @Override
    public Iterable<ValidatorMessage> validate(@NotNull String value) {
        final ArrayList<ValidatorMessage> messages = new ArrayList<>();

        if ((value.length() < 3) || (value.length() > 30)) {
            messages.add(new ValidatorMessage(ValidatorStatus.ERROR,
                    "Логин должен быть от 3 до 30 символов длиной."));
            return messages;
        }

        if (!value.matches("^[a-zA-Z0-9_]+$")) {
            messages.add(new ValidatorMessage(ValidatorStatus.ERROR,
                    "В логине можно использовать только заглавные и строчные латинские буквы (a-Z), " +
                    "цифры (0-9) и символ '_'."));
            return messages;
        }

        if (accountService.hasLogin(value)) {
            messages.add(new ValidatorMessage(ValidatorStatus.ERROR, "Логин " + value +
                    " уже используется."));
            return messages;
        }

        messages.add(new ValidatorMessage(ValidatorStatus.OK));
        return messages;
    }
}
