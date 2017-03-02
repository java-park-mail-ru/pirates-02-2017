package api.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ResponseGenerator {

    public static String toJSON(Object obj) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"status\":\"" + String.valueOf(ErrorCodes.JSON_ERROR) + "\"}";
        }
    }


    public static String toJSONWithStatus(Response resp, int status, String statusText) {
        resp.status = status;
        resp.error = statusText;

        return ResponseGenerator.toJSON(resp);
    }


    public static String toJSONWithStatus(Response resp, int status) {
        return ResponseGenerator.toJSONWithStatus(resp, status, "");
    }


    public static String toJSONWithStatus(Response resp) {
        return ResponseGenerator.toJSONWithStatus(resp, ErrorCodes.SUCCESS, "Ok");
    }

}
