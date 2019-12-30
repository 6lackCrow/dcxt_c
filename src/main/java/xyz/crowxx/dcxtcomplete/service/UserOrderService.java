package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.VO.FoodByOrderVO;
import xyz.crowxx.dcxtcomplete.model.UserOrder;
import xyz.crowxx.dcxtcomplete.repository.UserOrderRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class UserOrderService {
    @Resource
    UserOrderRepository userOrderRepository;
    @Resource
    FoodService foodService;

    public UserOrder addOrder(List<FoodByOrderVO> orderInfo, BigDecimal sumMonney) {
        return new UserOrder();
    }
}
