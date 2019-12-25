package xyz.crowxx.dcxtcomplete.Interceptor.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.crowxx.dcxtcomplete.Interceptor.LoginInterceptor;

import javax.annotation.Resource;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/admin",
                "/admin/login",
                "/admin/setadmin",
                "/bootstrap/**",
                "/jQuery/**",
                "/css/**",
                "/js/**",
                "/font-awesome/**",
                "/static/**",
                "/api/**",
                "/admin/food/**",
                "/admin/setting/index");
    }
}
