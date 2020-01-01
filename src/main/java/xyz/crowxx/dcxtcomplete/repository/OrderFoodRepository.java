package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.OrderFood;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderFoodRepository extends JpaRepository<OrderFood,Long> {
    @Query(value = "SELECT * FROM order_food WHERE order_id = ?1",nativeQuery = true)
    List<OrderFood> findOrderFoodsByOrder_id(Long oid);
    @Query(value = "SELECT * FROM order_food WHERE order_id = ?1 limit 0,1",nativeQuery = true)
    OrderFood findFoodByOrderIdAndFirstFood(Long id);
    @Transactional
    @Modifying
    @Query(value = "delete from order_food where order_id = ?1",nativeQuery = true)
    void deleteOrderFoodsByOid(Long order_id);
}
