package xyz.crowxx.dcxtcomplete.controller;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xyz.crowxx.dcxtcomplete.VO.FoodByOrderVO;
import xyz.crowxx.dcxtcomplete.core.exception.NotFoundException;
import xyz.crowxx.dcxtcomplete.model.Food;
import xyz.crowxx.dcxtcomplete.model.OrderFood;
import xyz.crowxx.dcxtcomplete.model.User;
import xyz.crowxx.dcxtcomplete.model.UserOrder;
import xyz.crowxx.dcxtcomplete.service.FoodService;
import xyz.crowxx.dcxtcomplete.service.OrderFoodService;
import xyz.crowxx.dcxtcomplete.service.UserOrderService;
import xyz.crowxx.dcxtcomplete.service.UserService;
import xyz.crowxx.dcxtcomplete.util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/food")
public class ApiOrderController {
    @Resource
    UserOrderService userOrderService;
    @Resource
    UserService userService;
    @Resource
    OrderFoodService orderFoodService;
    @Resource
    FoodService foodService;

    @Value("${xyz.crowxx.dcxt.dcxtcomplete.api.apfoodcontroller.server.address}")
    String serverAddress;
    @PostMapping("/order")
    public Object addOrder(@RequestBody RequestOrderBody order, HttpServletRequest request){
        List<FoodByOrderVO> orderInfo = order.getOrder();
        BigDecimal sumMonney = order.getSumMonney();
        Cookie[] cookies = request.getCookies();
        User user = new User();
        boolean flag = false;
        if (cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                User tmp = userService.findUserByOpenID(cookie.getValue());
                if (tmp!=null){
                    user = tmp;
                    flag = true;
                    break;
                }
            }
        }
        if(flag){
            UserOrder userOrder = userOrderService.addOrder(user,orderInfo,sumMonney);
            ResponseOrderInfo roi = new ResponseOrderInfo();
            roi.setOrder_id(userOrder.getId());
            return roi;
        }
        return new NotFoundException();
    }
    @GetMapping("/order")
    public Object getOrderInfo(@RequestParam("id")Long id){
        UserOrder order = userOrderService.findOrderById(id);
        ResponseOrderMsg orderMsg = new ResponseOrderMsg();
        BeanUtils.copyProperties(order,orderMsg);
        orderMsg.setSn(order.getId());
        orderMsg.setCode(order.getId());
        List<OrderFood> list = orderFoodService.findFoodByOrderId(order.getId());
        List<OrderFoods> foods = new ArrayList<>();
        for (OrderFood food : list) {
            OrderFoods ofs = new OrderFoods();
            Optional<Food> foodById = foodService.findFoodById(food.getFood_id());
            Food foodInfo = foodById.get();
            ofs.setFood_id(foodInfo.getId());
            ofs.setImage_url(serverAddress + "/static/static/uploads/" + foodInfo.getImage_url());
            ofs.setName(foodInfo.getName());
            ofs.setNumber(food.getNumber());
            ofs.setOrder_id(food.getOrder_id());
            ofs.setId(food.getId());
            ofs.setPrice(food.getPrice());
            foods.add(ofs);
        }
        orderMsg.setOrder_food(foods);
        //System.out.println(orderMsg.toString());
        return orderMsg;
    }

    @PostMapping("/pay")
    public Object pay(@RequestBody RequestPayBody payinfo){
        UserOrder order = userOrderService.findOrderById(payinfo.getId());
        order.setComment(payinfo.getComment());
        order.setIs_pay(1);
        order.setPay_time(DateUtil.getDate());
        User user =  userService.updataUser(order.getUser_id(),order.getPrice());
        userOrderService.updateOrder(order);
        return "{\"pay\":true}";
    }

    @GetMapping("/orderlist")
    public Object getOrderList(HttpServletRequest request,int last_id,int row){
        Cookie[] cookies = request.getCookies();
        User user = new User();
        boolean flag = false;
        if (cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                User tmp = userService.findUserByOpenID(cookie.getValue());
                if (tmp!=null){
                    user = tmp;
                    flag = true;
                    break;
                }
            }
        }
        if (flag){
            List<UserOrder> orders = userOrderService.findOrderByUserIdAndIsPayAndLimit(user.getId(),last_id,row);
            List<OrderMsg> list = new ArrayList<>();
            for (UserOrder order : orders) {
               OrderFood of = orderFoodService.findFoodByOrderIdAndFirstFood(order.getId());
               Optional<Food> foodOptional = foodService.findFoodById(of.getFood_id());
               Food food = foodOptional.get();
               OrderMsg orderMsg = new OrderMsg();
               BeanUtils.copyProperties(order,orderMsg);
               orderMsg.setFirst_food_name(food.getName());
               list.add(orderMsg);
            }
            ResponseOrderMsgList romsl = new ResponseOrderMsgList();
            romsl.setList(list);
            romsl.setLast_id(10);
            return romsl;

        }
        return new NotFoundException();
    }

    @GetMapping("/record")
    public Object getRecord(HttpServletRequest request){
        List<Record> list = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        User user = new User();
        boolean flag = false;
        if (cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                User tmp = userService.findUserByOpenID(cookie.getValue());
                if (tmp!=null){
                    user = tmp;
                    flag = true;
                    break;
                }
            }
        }
        if (flag){
            List<UserOrder> orders = userOrderService.findOrderByUserIdAndIsPay(user.getId());
            for (UserOrder order : orders) {
                Record record = new Record();
                record.setId(user.getId());
                record.setPay_time(order.getPay_time());
                record.setPrice(order.getPrice());
                list.add(record);
            }
            ResponseRecord responseRecord = new ResponseRecord();
            responseRecord.setList(list);
            return responseRecord;
        }else {
            return new NotFoundException();
        }

    }

}
@Data
class RequestOrderBody{
    private List<FoodByOrderVO> order;
    private BigDecimal sumMonney;
}
@Data
class ResponseOrderInfo{
    private Long order_id;
}
@Data
class ResponseOrderMsg{
    private Long id;
    private Long user_id;
    private BigDecimal price;
    private BigDecimal promotion;
    private Integer number;
    private Integer is_pay;
    private Integer is_taken;
    private String comment;
    private String create_time;
    private String pay_time;
    private String taken_time;
    private Long sn;
    private Long code;
    private List<OrderFoods> order_food;
}
@Data
class OrderFoods{
    private Long id;
    private Long order_id;
    private Long food_id;
    private String name;
    private BigDecimal price;
    private String image_url;
    private Integer number;
}
@Data
class RequestPayBody{
    private Long id;
    private String comment;
}
@Data
class OrderMsg{
    private Long id;
    private Long user_id;
    private BigDecimal price;
    private BigDecimal promotion;
    private Integer number;
    private Integer is_pay;
    private Integer is_taken;
    private String comment;
    private String create_time;
    private String pay_time;
    private String taken_time;
    private String first_food_name;
}
@Data
class ResponseOrderMsgList{
    private List<OrderMsg> list;
    private int last_id;
}







@Data
class Record{
    private Long id;
    private BigDecimal price;
    private String pay_time;
}
@Data
class ResponseRecord{
    private List<Record> list;
}