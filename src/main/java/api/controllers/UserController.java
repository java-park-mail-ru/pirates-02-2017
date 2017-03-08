package api.controllers;

import api.model.User;
import api.services.AccountService;
import api.utils.info.UserCreationInfo;
import api.utils.info.UserLoginInfo;
import api.utils.response.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import static api.controllers.SessionController.USER_ID;
import static api.controllers.SessionController.USER_LOGIN;

@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost", "http://127.0.0.1"})
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @NotNull
    private final AccountService accountService;

    public UserController(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * Вернуть пользователя по id
     * @param id идентификатор пользователя
     * @return json с сериализованным <code>User</code> объектом
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        final User user = accountService.getUserById(id);
        if (user == null) {
            return Response.userNotFound();
        }

        return Response.okWithUser(user, "success");
    }


    /**
     * Вернуть пользователя по логину
     * @param requestBody json логин
     * @return json user
     */
    @PostMapping("/getByLogin")
    public ResponseEntity<?> getUserbyLogin(@RequestBody UserLoginInfo requestBody) {
        final User user = accountService.getUserByLogin(requestBody.getLogin());

        if (user == null) {
            return Response.userNotFound();
        }

        return Response.okWithUser(user, "success");
    }

    /**
     * Зарегистрировать пользователя
     * @param requestBody <code>login, email, password</code> в формате json
     * @return json сообщение об исходе операции
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreationInfo requestBody) {
        if (accountService.createUser(requestBody)) {
            return Response.okWithLogin(requestBody.getLogin(), "User created");
        }
        return Response.userAlreadyExists();
    }


    /**
     * Изменение данных пользователя
     * @param requestBody тело запроса с новыми данными
     * @param session объект <code>HttpSession</code> сессии пользователя
     * @return json сообщение об исходе операции
     */
    @PostMapping("/change")
    public ResponseEntity<?> changeUser(@RequestBody UserCreationInfo requestBody, HttpSession session) {

//        final Object login;
//
//        try {
//            login = session.getAttribute(SessionController.USER_LOGIN);
//            final User currentUser = accountService.getUserByLogin(login.toString());
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

        // ToDO: дождаться ответа про обработку исключений! А пока так:
        final Object id = session.getAttribute(USER_ID);

        if (id instanceof Long) {
            if (!accountService.changeUserById((Long) id, requestBody)) {
                return Response.invalidSession();
            }
        } else {
            return Response.invalidSession();
        }

        return Response.ok("User info changed");
    }

    /**
     * Удалить пользователя по логину
     * @param session объект <code>HttpSession</code> сессии пользователя
     * @return json сообщение об исходе операции
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpSession session) {

        final Object id = session.getAttribute(USER_LOGIN);

        if (id instanceof Long) {
            if (!accountService.deleteUserbyId((Long) id)) {
                return Response.invalidSession();
            }
        } else {
            return Response.invalidSession();
        }

        // ToDO: пользователя же надо разлогинить?
        session.invalidate();
        return Response.ok("User deleted");
    }

}
