package api.utils.response;


import api.model.User;
import api.utils.ErrorCodes;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<?> ok(@NotNull String msg) {
        return ResponseEntity.ok(new ResponseBody(ErrorCodes.SUCCESS, msg));
    }

    public static ResponseEntity<?> okWithLogin(@NotNull String login, @NotNull String msg) {
        return ResponseEntity.ok(new LoginResponseBody(ErrorCodes.SUCCESS, login, msg));
    }

    public static ResponseEntity<?> okWithUser(@NotNull User user, @NotNull String msg) {
        return ResponseEntity.ok(new UserResponseBody(ErrorCodes.SUCCESS, user.getLogin(), user.getEmail(),
                user.getCreatedAt(), user.getUpdatedAt(), msg));
    }

    public static ResponseEntity<?> notFound(int status,@NotNull String msg) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(status, msg));
    }

    public static ResponseEntity<?> badRequest(int status,@NotNull String msg) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBody(status, msg));
    }

    public static ResponseEntity<?> userNotFound() {
        return Response.notFound(ErrorCodes.USER_NOT_FOUND, "User not found");
    }

    public static ResponseEntity<?> userAlreadyExists() {
        return Response.badRequest(ErrorCodes.USER_ALREADY_EXISTS, "User already exists");
    }

    public static ResponseEntity<?> invalidSession() {
        return Response.badRequest(ErrorCodes.SESSION_INVALID, "Invalid session");
    }

    public static ResponseEntity<?> badLoginOrPassword() {
        return Response.badRequest(ErrorCodes.BAD_LOGIN_OR_PASSWORD, "Bad login or password");
    }

    public static ResponseEntity<?> badValidator() {
        return Response.badRequest(ErrorCodes.BAD_VALIDATOR, "Bad validator");
    }

}
