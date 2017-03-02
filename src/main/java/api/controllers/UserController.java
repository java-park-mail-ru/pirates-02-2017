package api.controllers;

import api.model.User;
import api.services.AccountService;
import api.utils.ErrorCodes;
import api.utils.GetUserInfo;
import api.utils.Response;
import api.utils.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @NotNull
    private final AccountService accountService;

    public UserController(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Вернуть пользователя по логину
     * @param requestBody json логин
     * @return json user
     */
    @PostMapping("/getByLogin")
    public ResponseEntity<?> showUser(@RequestBody GetUserInfo requestBody) {
        final User user = accountService.getUserByLogin(requestBody.getLogin());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error-message\":\"user not found\"}");
        }

        return ResponseEntity.ok(user);
    }

    /**
     * Зарегистрировать пользователя
     * @param requestBody <code>login, email, password</code> в формате json
     * @return json сообщение об исходе операции
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody GetUserInfo requestBody) {
        if (accountService.register(requestBody.getLogin(), requestBody.getEmail(),
                requestBody.getPassword())) {

            return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(
                    new Response(),
                    ErrorCodes.SUCCESS,
                    "User created"
                    ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseGenerator.toJSONWithStatus(
                new Response(),
                ErrorCodes.USER_ALREADY_EXISTS,
                "User already exists"
        ));
    }

    /**
     * Удалить пользователя
     * @param requestBody login из json
     * @return json сообщение об исходе операции
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody GetUserInfo requestBody) {

        if (accountService.delete(requestBody.getLogin())) {
            return ResponseEntity.ok("{\"content\":\"successful delete user\"}");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error-message\":\"user not found\"}");
    }

}
