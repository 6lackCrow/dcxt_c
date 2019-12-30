package xyz.crowxx.dcxtcomplete.Interceptor;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.crowxx.dcxtcomplete.model.User;
import xyz.crowxx.dcxtcomplete.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service(value = " apiUserLoginInterceptor")
public class ApiUserLoginInterceptor implements HandlerInterceptor {
    @Resource
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                String userinfo = cookie.getValue();
                User user = userService.findUserByOpenID(userinfo);
                if (user!=null){
                    return true;
                }
            }
        }
        response.sendRedirect(request.getContextPath()+"/admin");
        return false;
    }
}
