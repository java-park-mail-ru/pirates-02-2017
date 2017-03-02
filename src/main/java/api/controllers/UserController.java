package api.controllers;

import api.model.User;
import api.services.AccountService;
import api.utils.ErrorCodes;
import api.utils.UserCreationInfo;
import api.utils.UserLoginInfo;
import api.utils.Response;
import api.utils.ResponseGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;

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
    public ResponseEntity<?> showUser(@RequestBody UserLoginInfo requestBody) {
        final User user = accountService.getUserByLogin(requestBody.getLogin());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseGenerator.toJSONWithStatus(
                    new Response(),
                    ErrorCodes.USER_NOT_FOUND,
                    "User not found"
            ));
        }

        return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(
                new Response() {
                    public String login = user.getLogin();
                    public String email = user.getEmail();
                    public long createdAt = user.getCreatedAt().toEpochSecond(ZoneOffset.UTC);
                    public long updatedAt = user.getUpdatedAt().toEpochSecond(ZoneOffset.UTC);
                }
        ));
    }

    /**
     * Зарегистрировать пользователя
     * @param requestBody <code>login, email, password</code> в формате json
     * @return json сообщение об исходе операции
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreationInfo requestBody) {
        if (accountService.createUser(requestBody.getLogin(), requestBody.getEmail(),
                requestBody.getPassword())) {

            return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(
                    new Response() {
                        public String login = requestBody.getLogin();
                    },
                    ErrorCodes.SUCCESS,
                    "User created"
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseGenerator.toJSONWithStatus(
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
    public ResponseEntity<?> deleteUser(@RequestBody UserLoginInfo requestBody) {

        if (accountService.deleteUser(requestBody.getLogin())) {
            return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(
                    new Response() {
                        public String login = requestBody.getLogin();
                    },
                    ErrorCodes.SUCCESS,
                    "User deleted"
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseGenerator.toJSONWithStatus(
                new Response(),
                ErrorCodes.USER_NOT_FOUND,
                "User not found"
        ));
    }

}
