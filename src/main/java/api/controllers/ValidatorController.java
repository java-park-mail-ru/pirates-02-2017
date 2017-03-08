package api.controllers;

import api.utils.info.ValidationInfo;
import api.utils.response.Response;
import api.utils.validator.Validator;
import api.utils.validator.ValidatorMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * Created by coon on 08.03.17.
 */

@RestController
@RequestMapping(path = "/validator")
public class ValidatorController {

    @NotNull
    private final ApplicationContext appContext;

    public ValidatorController(@NotNull ApplicationContext appContext) {
        this.appContext = appContext;
    }


    /**
     * Выполнить валидацию данных
     * @param name имя валидатора
     * @return json с результатом работы валидатора
     */
    @PostMapping("/{name}")
    public ResponseEntity<?> getUserById(@PathVariable String name, @RequestBody ValidationInfo validation) {
        final Object validator;

        try {
            validator = this.appContext.getBean(name + "Validator");

            if (!(validator instanceof Validator)) {
                throw new NoSuchBeanDefinitionException(name + "Validator");
            }
        } catch (NoSuchBeanDefinitionException e) {
            return Response.badValidator();
        }

        Iterable<ValidatorMessage> messages = ((Validator) validator).validate(validation.getValue());
        return ResponseEntity.ok(messages);
    }

}
