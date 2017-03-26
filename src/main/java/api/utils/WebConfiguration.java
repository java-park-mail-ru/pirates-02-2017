//package api.utils;
//
//
//import api.utils.test.TestHeaderInterceptor;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//
//@Configuration
//public class WebConfiguration extends WebMvcConfigurerAdapter {
//
//    private final TestHeaderInterceptor testHeaderInterceptor;
//
//
//    public WebConfiguration(@NotNull TestHeaderInterceptor testHeaderInterceptor){
//        this.testHeaderInterceptor = testHeaderInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(this.testHeaderInterceptor);
//    }
//
//}
