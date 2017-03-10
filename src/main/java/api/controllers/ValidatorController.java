package api.controllers;

import api.services.generic.AbstractAccountService;
import api.controllers.generic.ApplicationController;
import api.utils.info.ValueInfo;
import api.utils.response.*;
import api.utils.validator.generic.Validator;
import api.utils.validator.ValidatorMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "*", "http://127.0.0.1:3000"})
@RestController
@RequestMapping(path = "/validator")
public class ValidatorController extends ApplicationController {

    public ValidatorController(@NotNull AbstractAccountService accountService,
                               @NotNull ApplicationContext appContext) {
        super(accountService, appContext);
    }


    /**
     * Выполнить валидацию данных
     * @param name имя валидатора
     * @return json с результатом работы валидатора
     */
    @PostMapping("/{name}")
    public ResponseEntity<?> getUserById(@PathVariable String name, @RequestBody ValueInfo validation) {
        final Object validator;

        try {
            validator = this.appContext.getBean(name + "Validator");

            if (!(validator instanceof Validator)) {
                throw new NoSuchBeanDefinitionException(name + "Validator");
            }
        } catch (NoSuchBeanDefinitionException e) {
            return Response.badValidator();
        }

        final Iterable<ValidatorMessage> messages = ((Validator) validator).validate(validation.getValue());
        return ResponseEntity.ok(messages);
    }

}
