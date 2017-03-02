package api.controllers;

import api.model.User;
import api.services.AccountService;
import api.utils.ErrorCodes;
import api.utils.Response;
import api.utils.ResponseGenerator;
import api.utils.UserAuthInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/session")
public class SessionController {

    @NotNull
    private final AccountService accountService;
    public static final String USER_LOGIN = "USER_ID";

    public SessionController(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * Залогинить пользователя
     * @param requestBody login и password из тела запроса в json
     * @param session объект <code>HttpSession</code> сессии пользователя
     * @return json <code>User</code> ответ если OK, иначе <code>HTTP</code> код соответсвующей ошибки
     */

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserAuthInfo requestBody, HttpSession session) {

        final User user = accountService.authenticateUser(requestBody.getLogin(), requestBody.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseGenerator.toJSONWithStatus(
                    new Response(),
                    ErrorCodes.BAD_LOGIN_OR_PASSWORD,
                    "Bad login or password"
            ));
        }

        session.setAttribute(USER_LOGIN, user.getLogin());

        return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(
                new Response(),
                ErrorCodes.SUCCESS,
                "Logged in"
        ));
    }

    /**
     * Разлогин
     * @param session объект <code>HttpSession</code> сессии
     * @return json ответ если OK, иначе <code>HTTP</code> код соответсвующей ошибки
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(new Response()));
    }

    /**
     * Вернуть залогиненного пользователя
     * @param session объект <code>HttpSession</code> сессии
     * @return json <code>User</code>
     */
    @GetMapping("/current")
    public ResponseEntity<?> getLoggedUser(HttpSession session) {

        User currentUser;
        Object login;

        try {
            login = session.getAttribute(USER_LOGIN);
            currentUser = accountService.getUserByLogin(login.toString());

            if (currentUser == null) {
                throw new NullPointerException();
            }
        } catch (IllegalStateException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseGenerator.toJSONWithStatus(
                    new Response(),
                    ErrorCodes.SESSION_INVALID,
                    "Invalid session"
            ));
        }

        return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(
                new Response() {
                    public String login = currentUser.getLogin();
                },
                ErrorCodes.SUCCESS,
                "Ok"
        ));
    }
}
