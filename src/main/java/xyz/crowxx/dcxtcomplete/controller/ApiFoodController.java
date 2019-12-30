package xyz.crowxx.dcxtcomplete.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.crowxx.dcxtcomplete.VO.FoodListVo;
import xyz.crowxx.dcxtcomplete.VO.FoodVO;
import xyz.crowxx.dcxtcomplete.VO.PromotionVO;
import xyz.crowxx.dcxtcomplete.model.Category;
import xyz.crowxx.dcxtcomplete.model.Food;
import xyz.crowxx.dcxtcomplete.model.Setting;
import xyz.crowxx.dcxtcomplete.service.CategoryService;
import xyz.crowxx.dcxtcomplete.service.FoodService;
import xyz.crowxx.dcxtcomplete.service.SettingService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/food")
public class ApiFoodController {
    @Resource
    SettingService settingService;
    @Value("${xyz.crowxx.dcxt.dcxtcomplete.api.apfoodcontroller.server.address}")
    String serverAddress;

    @Resource
    FoodService foodService;

    @Resource
    CategoryService categoryService;
    @GetMapping("/index")
    public Object getIndex(HttpServletRequest request){
        Setting setting = settingService.findSettingByName("img_ad");
        Setting setting1 = settingService.findSettingByName("img_category");
        Setting setting2 = settingService.findSettingByName("img_swiper");
        String img_ad = "\"" + setting.getValue() + "\"";
        String img_ad_tmp = img_ad.replace("\"/","\""+serverAddress);
        String img_ad_tmp2 = "\"img_ad\":" + img_ad_tmp;
        String img_category = "\"img_category\":" + setting1.getValue().replace("\"/", "\""+serverAddress) ;
        String img_swiper = "\"img_swiper\":" + setting2.getValue().replace("\"/","\""+serverAddress) ;
        String json = "{" + img_swiper + "," + img_ad_tmp2 + "," + img_category +"}";
        return json;
    }
    /*
    * 微信小程序获取商品列表
    * */
    @GetMapping("/list")
    public Object getFoodList(){
//        private Long category_id; 类型id
//        private String category_name; 类型name
//        private List<Food> food; 该类型下的所有food
//        "promotion":{"k":50,"v":10} //优惠信息
        //首先找到类型，再找到该类型下的所有food
        List<Category> categories = categoryService.findAll();
        List<FoodListVo> foodListVos = new ArrayList<>();
        for (Category category : categories) {
            //找到该分类下所有上架的food
            FoodListVo foodListVo = new FoodListVo();
            List<Food> foods = foodService.findFoodsByCidAndStatus(category.getId(),1);
            List<Food> foodList = new ArrayList<>();
            List<FoodVO> foodVOS = new ArrayList<>();
            for (int i = 0; i < foods.size(); i++) {
                //foods.get(i).setImage_url(serverAddress  +"static/static/uploads/"+ foods.get(i).getImage_url());
                Food food = foods.get(i);
                food.setImage_url(serverAddress  +"static/static/uploads/"+ foods.get(i).getImage_url());
                /**/
                //foodList.add(food);

                FoodVO foodVO = new FoodVO();
                foodVO.setCategory_id(food.getCategory_id());
                foodVO.setImage_url(food.getImage_url());
                foodVO.setName(food.getName());
                foodVO.setPrice(food.getPrice());
                foodVO.setStatus(food.getStatus());
                foodVO.setCreate_time(food.getCreate_time());
                foodVO.setCount(0);
                foodVO.setId(food.getId());
                foodVOS.add(foodVO);
            }
            foodListVo.setCategory_id(category.getId());
            foodListVo.setCategory_name(category.getName());
            foodListVo.setFood(foodVOS);
            foodListVos.add(foodListVo);
        }
        System.out.println(foodListVos.toString());
        Setting setting = settingService.findSettingByName("promotion");
        ResponseFoodListParam responseFoodListParam = new ResponseFoodListParam();
        responseFoodListParam.setList(foodListVos);
        String promotion = setting.getValue().replace("[","").replace("]","");
        JSONObject jsonObject = JSON.parseObject(promotion);
        PromotionVO promotionVO = jsonObject.toJavaObject(PromotionVO.class);
        responseFoodListParam.setPromotion(promotionVO);

        return responseFoodListParam;
    }

}
@Data
class ResponseFoodListParam{
    List<FoodListVo> list;
    PromotionVO promotion;
}