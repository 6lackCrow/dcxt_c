package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.OrderFood;

import java.util.List;

public interface OrderFoodRepository extends JpaRepository<OrderFood,Long> {
    @Query(value = "SELECT * FROM order_food WHERE order_id = ?1",nativeQuery = true)
    List<OrderFood> findOrderFoodsByOrder_id(Long oid);

}
