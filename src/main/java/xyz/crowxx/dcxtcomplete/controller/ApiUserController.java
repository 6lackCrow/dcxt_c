package xyz.crowxx.dcxtcomplete.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.crowxx.dcxtcomplete.VO.GetUserInfoVO;
import xyz.crowxx.dcxtcomplete.VO.SettingVO;
import xyz.crowxx.dcxtcomplete.model.User;
import xyz.crowxx.dcxtcomplete.service.SettingService;
import xyz.crowxx.dcxtcomplete.service.UserService;
import xyz.crowxx.dcxtcomplete.util.HttpUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiUserController {
    @Resource
    SettingService settingService;
    @Resource
    UserService userService;
    @GetMapping("/user/setting")
    public SettingVO setting(HttpServletRequest request){
        SettingVO settingVO = new SettingVO();
        settingVO.setIsLogin(false);
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            settingVO.setIsLogin(true);
        }
        return settingVO;
    }
    @GetMapping("/user/login")
    public Object login(String js_code,HttpServletRequest request) throws IOException {
        /*访问接口获取登录信息*/
        String appid = settingService.findAppID();
        String appsecret = settingService.findAppSecret();
        String code = js_code;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appsecret+"&js_code="+code+"&grant_type=authorization_code";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        String result = HttpUtil.HttpRestClient(url,method,params);
        JSONObject jsonObject = JSON.parseObject(result);
        GetUserInfoVO userInfoVO = jsonObject.toJavaObject(GetUserInfoVO.class);
        /*保存登录信息*/
        User user = userService.findUserByOpenID(userInfoVO.getOpenid());
        if (user==null){
            userService.createUser(userInfoVO.getOpenid());
        }
        request.getSession().setAttribute("user",UUID.randomUUID().toString());
        SettingVO settingVO = new SettingVO();
        settingVO.setIsLogin(true);
        return settingVO;
    }



}



