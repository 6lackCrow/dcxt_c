package xyz.crowxx.dcxtcomplete.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.crowxx.dcxtcomplete.VO.FoodByOrderVO;
import xyz.crowxx.dcxtcomplete.model.UserOrder;
import xyz.crowxx.dcxtcomplete.service.UserOrderService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/food")
public class ApiOrderController {
    @Resource
    UserOrderService userOrderService;
    @PostMapping("/order")
    public Object addOrder(@RequestBody RequestOrderBody order){
        List<FoodByOrderVO> orderInfo = order.getOrder();
        BigDecimal sumMonney = order.getSumMonney();
        UserOrder userOrder = userOrderService.addOrder(orderInfo,sumMonney);

        System.out.println("orderinfo="+ orderInfo);
        System.out.println("sumMonney="+sumMonney);
        return "";
    }
}
@Data
class RequestOrderBody{
    private List<FoodByOrderVO> order;
    private BigDecimal sumMonney;
}