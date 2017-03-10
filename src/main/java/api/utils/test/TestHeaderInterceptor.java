package api.utils.test;


import api.Application;
import api.services.generic.AbstractAccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class TestHeaderInterceptor extends HandlerInterceptorAdapter {

    public final AbstractAccountService accountService;


    public TestHeaderInterceptor(@NotNull AbstractAccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        this.accountService.setTest(Application.SECRET_TEST_KEY.equals(
                request.getHeader("X-Test-Environment-Key")));
        return super.preHandle(request, response, handler);
    }

}
