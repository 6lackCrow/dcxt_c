package xyz.crowxx.dcxtcomplete.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.crowxx.dcxtcomplete.VO.ImgSwiperVO;
import xyz.crowxx.dcxtcomplete.VO.ImgcategoryVO;
import xyz.crowxx.dcxtcomplete.VO.PromotionVO;
import xyz.crowxx.dcxtcomplete.model.Admin;
import xyz.crowxx.dcxtcomplete.model.Setting;
import xyz.crowxx.dcxtcomplete.repository.SettingRepository;
import xyz.crowxx.dcxtcomplete.service.AdminService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AuthController {
    String head = "{\"list\":";
    String end = "}";
    @Resource
    AdminService adminService;
    @Resource
    SettingRepository settingRepository;
    @GetMapping
    public String login(){
        Boolean flag = adminService.isAlreadyExistsl();
        if (flag) {
            return "login";
        }else {
            return "setting/setting";
        }
    }
    @PostMapping("/login")
    public String login(String username, String password, HttpServletRequest request, Model model){
        Boolean flag = adminService.login(username,password,request);
        if (flag){
            model.addAttribute("msg","登录成功");
            return "redirect:/admin/index";
        }
        model.addAttribute("msg","用户名或密码错误");
        return "login";
    }
    @GetMapping("/index")
    public String getIndex(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("user");
        model.addAttribute("user",admin);
        return "admin/index";
    }

    @PostMapping("/setadmin")
    public String setAdmin(String admin_username,String admin_pwd1,String admin_pwd2,Model model){
        if (!admin_username.equals("")){
            if(admin_pwd1.equals("")){
                model.addAttribute("msg","密码不能为空!!!!!!!!!!!");
                return "setting/setting";
            }
            if(admin_pwd2.equals("")){
                model.addAttribute("msg","请输入确认密码!!!!!!!!");
                return "setting/setting";
            }
            if(admin_pwd1.equals(admin_pwd2)){
                adminService.setAdmin(admin_username,admin_pwd1);
                return "redirect:/admin";
            }else {
                model.addAttribute("msg","两次输入密码不一致");
                return "setting/setting";
            }
        }else {
            model.addAttribute("msg","用户名不能为空");
            return "setting/setting";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin";
    }

    @GetMapping("/main/index")
    public String getMainIndex(){
        return "admin/main/index";
    }

    @GetMapping("/setting/index")
    public String getSettingIndex(Model model){
        List<Setting> list = settingRepository.findAll();
        String appid = list.get(0).getValue();
        String appsecret =list.get(1).getValue();
        String img_ad = list.get(2).getValue();
        String img_category =  head + list.get(3).getValue() + end;
        List<String> img_categories = JSON.parseArray(JSON.parseObject(img_category).getString("list"),String.class);
        ImgcategoryVO[] imgcategoryVOS = new ImgcategoryVO[img_categories.size()];
        for (int i = 0;i < img_categories.size();i++){
            imgcategoryVOS[i] = new ImgcategoryVO();
            imgcategoryVOS[i].setNum(i+1);
            imgcategoryVOS[i].setImg_category(img_categories.get(i));
        }
        String img_swiper = head + list.get(4).getValue() + end;
        List<String> img_swipers = JSON.parseArray(JSON.parseObject(img_swiper).getString("list"),String.class);
        ImgSwiperVO[] imgSwiperVOS = new ImgSwiperVO[img_swipers.size()];
        for (int i = 0; i < img_swipers.size(); i++) {
            imgSwiperVOS[i] = new ImgSwiperVO();
            imgSwiperVOS[i].setNum(i+1);
            imgSwiperVOS[i].setImg_swiper(img_swipers.get(i));
        }
        String promotion = list.get(5).getValue().replace("[","").replace("]","");
        JSONObject jsonObject = JSON.parseObject(promotion);
        PromotionVO promotionVO = jsonObject.toJavaObject(PromotionVO.class);
        model.addAttribute("appid",appid);
        model.addAttribute("appsecret",appsecret);
        model.addAttribute("img_ad",img_ad);
        model.addAttribute("img_categories",imgcategoryVOS);
        model.addAttribute("img_swipers",imgSwiperVOS);
        model.addAttribute("promotion",promotionVO);
        return "admin/setting/index";
    }
}
