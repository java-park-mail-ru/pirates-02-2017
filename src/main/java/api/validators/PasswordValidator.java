package api.validators;

import api.utils.validator.Validator;
import api.utils.validator.ValidatorMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//import org.springframework.context.annotation.Bean;

/**
 * Created by coon on 08.03.17.
 */

public class PasswordValidator implements Validator {

    @Override
    @NotNull
    public Iterable<ValidatorMessage> validate(@NotNull String value) {
        return new ArrayList<>();
    }

}
