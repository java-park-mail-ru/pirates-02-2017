package api.controllers.generic;

import api.services.generic.AbstractService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;


public class ApplicationController {

    protected final AbstractService accountService;
    protected final ApplicationContext appContext;

    public ApplicationController(@NotNull AbstractService accountService,
                             @NotNull ApplicationContext appContext) {
        this.accountService = accountService;
        this.appContext = appContext;
    }

}
