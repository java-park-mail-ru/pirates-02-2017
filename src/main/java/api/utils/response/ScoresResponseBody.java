package api.utils.response;


import api.utils.ErrorCodes;
import api.utils.response.generic.ResponseBody;

import java.util.Map;

public class ScoresResponseBody extends ResponseBody {

    public final Map<String, Integer> scores;

    public ScoresResponseBody(Map<String, Integer> scores) {
        super(ErrorCodes.SUCCESS, "ok");
        this.scores = scores;
    }
}