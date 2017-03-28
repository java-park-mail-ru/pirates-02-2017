package api.controllers.generic;

import api.services.AccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;


public class ApplicationController {

    protected final AccountService accountService;
    protected final ApplicationContext appContext;

    public ApplicationController(@NotNull AccountService accountService,
                             @NotNull ApplicationContext appContext) {
        this.accountService = accountService;
        this.appContext = appContext;
    }

}
