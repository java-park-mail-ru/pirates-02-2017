package api.controllers;

import api.model.User;
import api.services.AccountService;
import api.utils.UserCreationInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/sessions")
public class SessionController {

    @NotNull
    private final AccountService accountService;
    private static final String USER_LOGIN = "USER_ID";

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
    public ResponseEntity<?> loginUser(@RequestBody UserCreationInfo requestBody, HttpSession session) {

        // некорректный запрос
        if (requestBody.getLogin() == null || requestBody.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // аутентификация
        final User user = accountService.authenticateUser(requestBody.getLogin(), requestBody.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        session.setAttribute(USER_LOGIN, user.getLogin());

        return ResponseEntity.ok("{\"content\":\"successful login\"}");
    }

    /**
     * Разлогин
     * @param session объект <code>HttpSession</code> сессии
     * @return json ответ если OK, иначе <code>HTTP</code> код соответсвующей ошибки
     */
    @DeleteMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {

        // если user не нашелся
        if (session.getAttribute(USER_LOGIN) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        session.invalidate();
        return ResponseEntity.ok("{\"content\":\"Goodbye!\"}");
    }

    /**
     * Вернуть залогиненного пользователя
     * @param session объект <code>HttpSession</code> сессии
     * @return json <code>User</code>
     */
    @GetMapping("/current")
    public ResponseEntity<?> getLoggedUser(HttpSession session) {

        final User currentUser;
        final Object login = session.getAttribute(USER_LOGIN);

        // атрибут сессии существует
        if (login instanceof String) {
            currentUser = accountService.getUserByLogin((String) login);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // если пользователь не нашелся
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(currentUser);
    }
}
