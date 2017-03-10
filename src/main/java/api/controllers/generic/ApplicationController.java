package api.controllers.generic;

import api.services.generic.AbstractAccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;


public class ApplicationController {

    protected final AbstractAccountService accountService;
    protected final ApplicationContext appContext;

    public ApplicationController(@NotNull AbstractAccountService accountService,
                             @NotNull ApplicationContext appContext) {
        this.accountService = accountService;
        this.appContext = appContext;
    }

}
