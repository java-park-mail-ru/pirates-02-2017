package api.controllers;

import api.model.User;
import api.services.generic.AbstractAccountService;
import api.controllers.generic.ApplicationController;
import api.utils.info.*;
import api.utils.response.*;
import api.utils.response.generic.ResponseBody;
import api.utils.validator.ValidatorChain;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

import static api.controllers.SessionController.USER_ID;

@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "*", "http://127.0.0.1:3000"})
@RestController
@RequestMapping(path = "/user")
public final class UserController extends ApplicationController {

    public UserController(@NotNull AbstractAccountService accountService,
                          @NotNull ApplicationContext appContext) {
        super(accountService, appContext);
    }


    /**
     * Вернуть пользователя по id
     * @param requestBody идентификатор пользователя
     * @return json с сериализованным <code>User</code> объектом
     */
    @PostMapping("/getById")
    public ResponseEntity<? extends ResponseBody> getUserById(@RequestBody UserIdInfo requestBody) {
        final User user = accountService.getUserById(requestBody.getId());
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
    public ResponseEntity<? extends ResponseBody> createUser(@RequestBody UserCreationInfo requestBody) {

        if (!ValidatorChain.isValid(requestBody, false, this.appContext)) {
            return Response.badValidator();
        }

        accountService.createUser(requestBody);
        return Response.ok("User created");
        //return Response.userAlreadyExists();
    }

    /**
     * Изменить логин пользователя текущей сессии
     * @param requestBody тело запроса с логином
     * @param session объект сессии
     * @return json с результатом работы
     */
    @PostMapping("/changeLogin")
    public ResponseEntity<? extends ResponseBody> changeUserLogin(@RequestBody UserLoginInfo requestBody,
                                                                  HttpSession session) {
        if (ValidatorChain.isValid(requestBody, false, appContext)) {
            Object id = session.getAttribute(USER_ID);
            if (id instanceof Long) {
                accountService.changeLogin((Long) id, requestBody.getValue());
                return Response.ok("Login changed");
            } else {
                return Response.invalidSession();
            }
        } else {
            return Response.badValidator();
        }
    }

    /**
     * Изменить email пользователя текущей сессии
     * @param requestBody тело запроса с новым email
     * @param session объект сессии
     * @return json сообщение об исходи операции
     */
    @PostMapping("/changeEmail")
    public ResponseEntity<? extends ResponseBody> changeUserEmail(@RequestBody UserEmailInfo requestBody,
                                                                  HttpSession session) {
        if (ValidatorChain.isValid(requestBody, false, appContext)) {
            Object id = session.getAttribute(USER_ID);
            if (id instanceof Long) {
                accountService.changeEmail((Long) id, requestBody.getValue());
                return Response.ok("Email changed");
            } else {
                return Response.invalidSession();
            }
        } else {
            return Response.badValidator();
        }
    }

    /**
     * Изменить пароль пользователя текущей сессии
     * @param requestBody тело запроса с новым паролем
     * @param session объект сессии
     * @return json сообщение о работе
     */
    @PostMapping("/changePassword")
    public ResponseEntity<? extends ResponseBody> changeUserPassword(@RequestBody UserPasswordInfo requestBody,
                                                                     HttpSession session) {
        if (ValidatorChain.isValid(requestBody, false, appContext)) {
            Object id = session.getAttribute(USER_ID);
            if (id instanceof Long) {
                accountService.changePassword((Long) id, requestBody.getValue());
                return Response.ok("Login changed");
            } else {
                return Response.invalidSession();
            }
        } else {
            return Response.badValidator();
        }
    }

    /**
     * Удалить пользователя по логину
     * @param session объект <code>HttpSession</code> сессии пользователя
     * @return json сообщение об исходе операции
     */
    @PostMapping("/delete")
    public ResponseEntity<? extends ResponseBody> deleteUser(HttpSession session) {

        final Object id = session.getAttribute(USER_ID);

        if (id instanceof Long) {
            if (!accountService.deleteUserbyId((Long) id)) {
                return Response.invalidSession();
            }
        } else {
            return Response.invalidSession();
        }

        session.invalidate();
        return Response.ok("User deleted");
    }

}
