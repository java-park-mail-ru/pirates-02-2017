package api.controllers.generic;

import api.services.generic.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;


public class ApplicationController {

    protected final UserService accountService;
    protected final ApplicationContext appContext;

    public ApplicationController(@NotNull UserService accountService,
                             @NotNull ApplicationContext appContext) {
        this.accountService = accountService;
        this.appContext = appContext;
    }

}
