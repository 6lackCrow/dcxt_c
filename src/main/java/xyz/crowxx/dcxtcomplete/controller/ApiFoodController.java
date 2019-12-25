package xyz.crowxx.dcxtcomplete.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.crowxx.dcxtcomplete.model.Setting;
import xyz.crowxx.dcxtcomplete.service.SettingService;

import javax.annotation.Resource;
@RestController
@RequestMapping("/api/food")
public class ApiFoodController {
    @Resource
    SettingService settingService;
    @GetMapping("/index")
    public Object getIndex(){
        Setting setting = settingService.findSettingByName("img_ad");
        Setting setting1 = settingService.findSettingByName("img_category");
        Setting setting2 = settingService.findSettingByName("img_swiper");
        String img_ad = "\"" + setting.getValue() + "\"";
        String img_ad_tmp = img_ad.replace("\"/","\"http://localhost:8080/");
        String img_ad_tmp2 = "\"img_ad\":" + img_ad_tmp;
        String img_category = "\"img_category\":" + setting1.getValue().replace("\"/","\"http://localhost:8080/") ;
        String img_swiper = "\"img_swiper\":" + setting2.getValue().replace("\"/","\"http://localhost:8080/") ;
        String json = "{" + img_swiper + "," + img_ad_tmp2 + "," + img_category +"}";
        return json;
    }


}