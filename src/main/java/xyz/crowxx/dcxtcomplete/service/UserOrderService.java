package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.VO.FoodByOrderVO;
import xyz.crowxx.dcxtcomplete.model.OrderFood;
import xyz.crowxx.dcxtcomplete.model.User;
import xyz.crowxx.dcxtcomplete.model.UserOrder;
import xyz.crowxx.dcxtcomplete.repository.OrderFoodRepository;
import xyz.crowxx.dcxtcomplete.repository.UserOrderRepository;
import xyz.crowxx.dcxtcomplete.util.DateUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserOrderService {
    @Resource
    UserOrderRepository userOrderRepository;
    @Resource
    OrderFoodRepository orderFoodRepository;

    public UserOrder addOrder(User user, List<FoodByOrderVO> orderInfo, BigDecimal sumMonney) {
        UserOrder userOrder = new UserOrder();
        userOrder.setCreate_time(DateUtil.getDate());
        userOrder.setUser_id(user.getId());
        userOrder.setIs_pay(0);
        userOrder.setIs_taken(0);
        if (sumMonney.compareTo(BigDecimal.valueOf(50))>-1){
            sumMonney = sumMonney.subtract(BigDecimal.valueOf(10));
        }
        userOrder.setPrice(sumMonney);
        if(sumMonney.compareTo(BigDecimal.valueOf(50))>-1){
            userOrder.setPromotion(BigDecimal.valueOf(10));
        }
        else {
            userOrder.setPromotion(BigDecimal.valueOf(0));
        }
        int number = 0;
        for (int i = 0; i < orderInfo.size(); i++) {
            number++;
        }
        userOrder.setNumber(number);
        userOrder.setComment("");
        UserOrder order = userOrderRepository.save(userOrder);

        Long orderId = order.getId();
        for (FoodByOrderVO foodByOrderVO : orderInfo) {
            OrderFood food = new OrderFood();
            food.setFood_id(foodByOrderVO.getId());
            food.setNumber(foodByOrderVO.getNumber());
            food.setPrice(foodByOrderVO.getPrice());
            food.setOrder_id(orderId);
            orderFoodRepository.save(food);
        }
        return order;
    }

    public UserOrder findOrderById(Long id) {
        Optional<UserOrder> optionalUserOrder = userOrderRepository.findById(id);
        return optionalUserOrder.get();
    }

    public void updateOrder(UserOrder order) {
        userOrderRepository.save(order);
    }

    public List<UserOrder> findOrderByUserIdAndIsPay(Long id) {
        return userOrderRepository.findOrderByUserIdAndIsPay(id);
    }
}
