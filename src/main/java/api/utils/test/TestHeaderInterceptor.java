//package api.utils.test;
//
//
//import api.Application;
//import api.services.generic.AbstractService;
//import api.services.generic.UserService;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//@Component
//public class TestHeaderInterceptor extends HandlerInterceptorAdapter {
//    // vileven: заменил public на final
//    private final UserService accountService;
//
//
//    public TestHeaderInterceptor(@NotNull UserService accountService) {
//        this.accountService = accountService;
//    }
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
//                             Object handler) throws Exception {
//
//        this.accountService.setTest(Application.SECRET_TEST_KEY.equals(
//                request.getHeader("X-Test-Environment-Key")));
//        return super.preHandle(request, response, handler);
//    }
//
//}
