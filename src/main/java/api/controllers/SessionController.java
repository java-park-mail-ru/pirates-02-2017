package api.controllers;

import api.model.User;
import api.services.generic.AbstractAccountService;
import api.controllers.generic.ApplicationController;
import api.utils.response.*;
import api.utils.info.UserAuthInfo;
import api.utils.response.generic.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "*", "http://127.0.0.1:3000"})
@RestController
@RequestMapping(path = "/session")
public class SessionController extends ApplicationController {

    public static final String USER_ID = "USER_ID";

    public SessionController(@NotNull AbstractAccountService accountService,
                             @NotNull ApplicationContext appContext) {
        super(accountService, appContext);
    }


    /**
     * Залогинить пользователя
     * @param requestBody login и password из тела запроса в json
     * @param session объект <code>HttpSession</code> сессии пользователя
     * @return json <code>User</code> ответ если OK, иначе <code>HTTP</code> код соответсвующей ошибки
     */

    @PostMapping("/login")
    public ResponseEntity<? extends ResponseBody> loginUser(@RequestBody UserAuthInfo requestBody, HttpSession session) {

        final User user = accountService.authenticateUser(requestBody.getLoginOrEmail(), requestBody.getPassword());
        if (user == null) {
            return Response.badLoginOrPassword();
        }

        session.setAttribute(USER_ID, user.getId());

        return Response.ok("Logged in");
    }

    /**
     * Разлогин
     * @param session объект <code>HttpSession</code> сессии
     * @return json ответ если OK, иначе <code>HTTP</code> код соответсвующей ошибки
     */
    @PostMapping("/logout")
    public ResponseEntity<? extends ResponseBody> logoutUser(HttpSession session) {
        session.invalidate();
        return Response.ok("User deleted");
    }

    /**
     * Вернуть залогиненного пользователя
     * @param session объект <code>HttpSession</code> сессии
     * @return json <code>User</code>
     */
    @GetMapping("/current")
    public ResponseEntity<? extends ResponseBody> getLoggedUser(HttpSession session) {

        final User currentUser;
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
