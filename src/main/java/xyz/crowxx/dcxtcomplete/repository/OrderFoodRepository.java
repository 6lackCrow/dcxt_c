package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.crowxx.dcxtcomplete.model.OrderFood;

public interface OrderFoodRepository extends JpaRepository<OrderFood,Long> {

}
