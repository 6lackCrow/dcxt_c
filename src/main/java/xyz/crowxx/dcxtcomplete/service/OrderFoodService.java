package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.repository.OrderFoodRepository;

import javax.annotation.Resource;

@Service
public class OrderFoodService {
    @Resource
    OrderFoodRepository orderFoodRepository;

}
