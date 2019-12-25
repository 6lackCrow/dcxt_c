package xyz.crowxx.dcxtcomplete.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.crowxx.dcxtcomplete.model.Setting;
import xyz.crowxx.dcxtcomplete.service.AdminService;
import xyz.crowxx.dcxtcomplete.service.SettingService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminSettingIndexController {
    @Resource
    AdminService adminService;
    @Resource
    SettingService settingService;
    @PostMapping("/setting/updatasetting")
    public String updateSetting(RequestPara para, HttpServletResponse response) throws IOException {
        String img_swiper1 = para.img_swiper1;
        String img_swiper2 = para.img_swiper2;
        String img_swiper3 = para.img_swiper3;
        String img_category1 = para.img_category1;
        String img_category2 = para.img_category2;
        String img_category3 = para.img_category3;
        String img_category4 = para.img_category4;
        String img_swiper = "[" +"\"" + img_swiper1 + "\"" + "," +"\"" + img_swiper2 + "\""+","+ "\"" + img_swiper3 + "\""+"]";
        String img_category = "[" +"\"" + img_category1 + "\"" + "," +"\"" + img_category2 + "\""+","+ "\"" + img_category3 + "\""+","+ "\"" + img_category4 + "\""+"]";
        String promotion = "[" + "{" + "\"k\":"+ para.promotion_k +"," + "\"v\":" + para.promotion_v+"}" + "]";
        String img_ad = para.img_ad;
        String appid = para.appid;
        String appsecret = para.appsecret;

        List<String> stringList = new ArrayList<>();
        stringList.add(appid);
        stringList.add(appsecret);
        stringList.add(img_ad);
        stringList.add(img_category);
        stringList.add(img_swiper);
        stringList.add(promotion);


        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Boolean flag = settingService.updateSetting(stringList);
        out.flush();
        if (flag){
            out.print("<script language=\"javascript\">alert('修改成功');window.location.href='/admin/setting/index'</script>");
            return "redirect:/admin/setting/index";
        }
        out.print("<script language=\"javascript\">alert('修改失败');window.location.href='127.0.0.1:8080/admin/setting/index'</script>");
        return "redirect:/admin/setting/index";
    }
}
@Data
class RequestPara{
    String appid;
    String appsecret;
    Integer promotion_k;
    Integer promotion_v;
    String img_swiper1;
    String img_swiper2;
    String img_swiper3;
    String img_ad;
    String img_category1;
    String img_category2;
    String img_category3;
    String img_category4;
}
