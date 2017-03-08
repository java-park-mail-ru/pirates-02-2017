package api.controllers;

import api.model.User;
import api.services.AccountService;
import api.utils.response.Response;
import api.utils.info.UserAuthInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping(path = "/session")
public class SessionController {

    @NotNull
    private final AccountService accountService;
    public static final String USER_LOGIN = "USER_LOGIN";
    public static final String USER_ID = "USER_ID";

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
            return Response.badLoginOrPassword();
        }

        session.setAttribute(USER_LOGIN, user.getLogin());
        session.setAttribute(USER_ID, user.getId());

        return Response.ok("Logged in");
    }

    /**
     * Разлогин
     * @param session объект <code>HttpSession</code> сессии
     * @return json ответ если OK, иначе <code>HTTP</code> код соответсвующей ошибки
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate();
        return Response.ok("User deleted");
    }

    /**
     * Вернуть залогиненного пользователя
     * @param session объект <code>HttpSession</code> сессии
     * @return json <code>User</code>
     */
    @GetMapping("/current")
    public ResponseEntity<?> getLoggedUser(HttpSession session) {

//        try {
//            login = session.getAttribute(USER_LOGIN);
//            currentUser = accountService.getUserByLogin(login.toString());
//
//            if (currentUser == null) {
//                throw new NullPointerException();
//            }
//        } catch (IllegalStateException | NullPointerException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseGenerator.toJSONWithStatus(
//                    new Response(status, error),
//                    ErrorCodes.SESSION_INVALID,
//                    "Invalid session"
//            ));
//        }
//
//        return ResponseEntity.ok(ResponseGenerator.toJSONWithStatus(
//                new Response(status, error) {
//                    public String login = currentUser.getLogin();
//                },
//                ErrorCodes.SUCCESS,
//                "Ok"
//        ));

        final User currentUser;
        final Object login;

        final Object id = session.getAttribute(USER_ID);

        if (id instanceof Long) {
            currentUser = accountService.getUserById((Long) id);
            if (currentUser == null) {
                return Response.invalidSession();
            }
        } else {
            return Response.invalidSession();
        }

        return Response.okWithUser(currentUser, "success");


    }
}
