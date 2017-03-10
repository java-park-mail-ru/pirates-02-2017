package api.controllers;


import api.controllers.generic.ApplicationController;
import api.model.User;
import api.services.generic.AbstractAccountService;
import api.utils.ErrorCodes;
import api.utils.info.UserAuthInfo;
import api.utils.response.Response;
import api.utils.response.generic.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "*", "http://127.0.0.1:3000"})
@RestController
@RequestMapping(path = "/test")
public class TestController extends ApplicationController {

    public TestController(@NotNull AbstractAccountService accountService,
                          @NotNull ApplicationContext appContext) {
        super(accountService, appContext);
    }


    @PostMapping("/start")
    public ResponseEntity<? extends ResponseBody> start(HttpSession session) {

        if (!this.accountService.isTest()) {
            return Response.badRequest(ErrorCodes.WRONG_ENVIRONMENT,
                    "Attempt to drop production!");
        }

        this.accountService.drop();
        return Response.ok("Test database dropped.");
    }

}
