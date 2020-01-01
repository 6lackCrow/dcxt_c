package xyz.crowxx.dcxtcomplete.controller;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.crowxx.dcxtcomplete.model.Food;
import xyz.crowxx.dcxtcomplete.model.OrderFood;
import xyz.crowxx.dcxtcomplete.model.UserOrder;
import xyz.crowxx.dcxtcomplete.service.FoodService;
import xyz.crowxx.dcxtcomplete.service.OrderFoodService;
import xyz.crowxx.dcxtcomplete.service.UserOrderService;
import xyz.crowxx.dcxtcomplete.util.DateUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    @Resource
    UserOrderService userOrderService;
    @Resource
    OrderFoodService orderFoodService;
    @Resource
    FoodService foodService;
    @GetMapping("/index")
    public String getOrderIndex(Model model, Integer type, String search, Integer pageNow){
      /*
               * type=0 显示所有
                * type=1 已付款未发货
               * type=2 已付款
                * type=3 已发货
               * type=4 未付款
                */
        if (search.equals("")){
            List<UserOrder> list = userOrderService.findOrderByType(type);
            int pageSize = 10;
            int lineCount = list.size();
            int pageCount = lineCount % pageSize ==0 ? lineCount/pageSize :  (lineCount/pageSize)+1;
            int[] pageList = new int[pageCount];
            for (int i = 0; i < pageCount; i++) {
                pageList[i] = i+1;
            }
            List<UserOrder> orderList = userOrderService.findOrderByTypeAndPage(type,pageSize*(pageNow-1),pageSize);
            List<Orders> orders = new ArrayList<>();
            for (UserOrder userOrder : orderList) {
                Orders o = new Orders();
                BeanUtils.copyProperties(userOrder,o);
                List<FoodByOrder> fbos = new ArrayList<>();
                List<OrderFood> ofs = orderFoodService.findFoodByOrderId(userOrder.getId());
                for (OrderFood of : ofs) {
                    FoodByOrder food = new FoodByOrder();
                    Optional<Food> foodOptional = foodService.findFoodById(of.getFood_id());
                    if (!foodOptional.isEmpty()){
                        food.setName(foodOptional.get().getName());
                        food.setNumber(of.getNumber());
                        food.setPrice(foodOptional.get().getPrice());
                        fbos.add(food);
                    }else{
                        food.setName("已删除的商品");
                        food.setNumber(0);
                        food.setPrice(new BigDecimal(0));
                        fbos.add(food);
                    }
                }
                o.setOrder_food(fbos);
                orders.add(o);
            }
            /*model.addAttribute("pageNow",pageNow).addAttribute("pageBack",pageNow-1).addAttribute("pageNext",pageNow+1)
                    .addAttribute("pageMax",pageCount).addAttribute("categoryid",categoryid).addAttribute("search",search);*/
            model.addAttribute("orders",orders)
                    .addAttribute("type",type)
                    .addAttribute("pageNow",pageNow)
                    .addAttribute("pageBack",pageNow-1)
                    .addAttribute("pageNext",pageNow+1)
                    .addAttribute("pageMax",pageCount)
                    .addAttribute("pageList",pageList).addAttribute("search",search);
                System.out.println(orders.toString());
        }else {
            Long id = Long.parseLong(search);
            Optional<UserOrder> order = userOrderService.findOrderByOid(id);
            Orders o = new Orders();
            List<FoodByOrder> fbos = new ArrayList<>();
            List<Orders> orders = new ArrayList<>();
            if (!order.isEmpty()){
                BeanUtils.copyProperties(order.get(),o);
                List<OrderFood> ofs = orderFoodService.findFoodByOrderId(order.get().getId());
                for (OrderFood of : ofs) {
                    Optional<Food> foodOptional = foodService.findFoodById(of.getFood_id());
                    FoodByOrder fbo = new FoodByOrder();
                    if(!foodOptional.isEmpty()){
                        fbo.setPrice(foodOptional.get().getPrice());
                        fbo.setNumber(of.getNumber());
                        fbo.setName(foodOptional.get().getName());
                        fbos.add(fbo);
                    }else {
                        fbo.setName("已删除的商品");
                        fbo.setNumber(0);
                        fbo.setPrice(new BigDecimal(0));
                        fbos.add(fbo);
                    }
                }
                o.setOrder_food(fbos);
                orders.add(o);
            }
            model.addAttribute("orders",orders)
                    .addAttribute("type",type)
                    .addAttribute("pageNow",pageNow)
                    .addAttribute("pageBack",pageNow-1)
                    .addAttribute("pageNext",pageNow+1)
                    .addAttribute("pageMax",1)
                    .addAttribute("pageList",new int[]{1}).addAttribute("search",search);
            System.out.println(orders.toString());
        }
        return "admin/order/index";
    }
    @GetMapping("/sendoutgoods")
    public String sendOutGoods(Long id, Integer type, String search,Integer pageNow, RedirectAttributes attr){
        UserOrder userOrder = userOrderService.findOrderById(id);
        userOrder.setTaken_time(DateUtil.getDate());
        userOrder.setIs_taken(1);
        userOrderService.updateOrder(userOrder);
        attr.addAttribute("type", type).addAttribute("search",search).addAttribute("pageNow",pageNow);
        return "redirect:/admin/order/index";
    }
    @GetMapping("/deletefood")
    public String deleteFood(Long id, Integer type, String search,Integer pageNow, RedirectAttributes attr){
        userOrderService.deleteOrderById(id);
        orderFoodService.deleteOrderFoodByOrderId(id);
        attr.addAttribute("type", type).addAttribute("search",search).addAttribute("pageNow",pageNow);
        return "redirect:/admin/order/index";
    }
    @GetMapping("/cancelshipment")
    public String cancelShipment(Long id, Integer type, String search,Integer pageNow, RedirectAttributes attr){
        UserOrder userOrder = userOrderService.findOrderById(id);
        userOrder.setIs_taken(0);
        userOrder.setTaken_time(null);
        userOrderService.updateOrder(userOrder);
        attr.addAttribute("type", type).addAttribute("search",search).addAttribute("pageNow",pageNow);
        return "redirect:/admin/order/index";
    }
}
@Data
class Orders{
    private Long id;
    private BigDecimal price;
    private BigDecimal promotion;
    private Integer number;
    private Integer is_pay;
    private Integer is_taken;
    private String comment;
    private String create_time;
    private String pay_time;
    private String taken_time;
    private List<FoodByOrder> order_food;
}
@Data
class FoodByOrder{
    private String name;
    private BigDecimal price;
    private Integer number;
}
