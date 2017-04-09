package api.utils.response;


import api.model.User;
import api.utils.ErrorCodes;
import api.utils.response.generic.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneOffset;

public class Response {

    public static ResponseEntity<? extends ResponseBody> ok(@NotNull String msg) {
        return ResponseEntity.ok(new ResponseBody(ErrorCodes.SUCCESS, msg));
    }

    public static ResponseEntity<? extends ResponseBody> okWithLogin(@NotNull String login, @NotNull String msg) {
        return ResponseEntity.ok(new LoginResponseBody(ErrorCodes.SUCCESS, login, msg));
    }

    public static ResponseEntity<? extends ResponseBody> okWithUser(@NotNull User user, @NotNull String msg) {
        return ResponseEntity.ok(new UserResponseBody(ErrorCodes.SUCCESS, user.getLogin(), user.getEmail(),
                user.getCreatedAt().toEpochSecond(ZoneOffset.UTC), user.getUpdatedAt().toEpochSecond(ZoneOffset.UTC), msg));
    }

    public static ResponseEntity<? extends ResponseBody> notFound(int status,@NotNull String msg) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody(status, msg));
    }

    public static ResponseEntity<? extends ResponseBody> badRequest(int status,@NotNull String msg) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody(status, msg));
    }

    public static ResponseEntity<? extends ResponseBody> userNotFound() {
        return Response.notFound(ErrorCodes.USER_NOT_FOUND, "User not found");
    }

    public static ResponseEntity<? extends ResponseBody> userAlreadyExists() {
        return Response.badRequest(ErrorCodes.USER_ALREADY_EXISTS, "User already exists");
    }

    public static ResponseEntity<? extends ResponseBody> invalidSession() {
        return Response.badRequest(ErrorCodes.SESSION_INVALID, "Invalid session");
    }

    public static ResponseEntity<? extends ResponseBody> badLoginOrPassword() {
        return Response.badRequest(ErrorCodes.BAD_LOGIN_OR_PASSWORD, "Bad login or password");
    }

    public static ResponseEntity<? extends ResponseBody> badValidator() {
        return Response.badRequest(ErrorCodes.BAD_VALIDATOR, "Bad validator");
    }

}
