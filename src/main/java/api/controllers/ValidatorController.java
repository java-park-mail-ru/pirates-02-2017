package api.controllers;

import api.utils.validator.Validator;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getUserById(@NotNull @PathVariable String name) {
        Validator validator = (Validator) this.appContext.getBean(name + "Validator");
        return null;
    }

}
