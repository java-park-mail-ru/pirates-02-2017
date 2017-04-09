package api.controllers.generic;

import api.services.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = {"https://tp314rates.herokuapp.com", "https://project-motion.herokuapp.com",
        "http://localhost:3000", "*", "http://127.0.0.1:3000"})
public abstract class ApplicationController {

    protected final AccountService accountService;
    protected final ApplicationContext appContext;


    public ApplicationController(AccountService accountService, ApplicationContext appContext) {
        this.accountService = accountService;
        this.appContext = appContext;
    }


    public ApplicationController(ApplicationContext appContext) {
        //noinspection ConstantConditions
        this(null, appContext);
    }

}
