package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.model.OrderFood;
import xyz.crowxx.dcxtcomplete.repository.OrderFoodRepository;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderFoodService {
    @Resource
    OrderFoodRepository orderFoodRepository;

    public List<OrderFood> findFoodByOrderId(Long id) {
        return orderFoodRepository.findOrderFoodsByOrder_id(id);
    }
}
