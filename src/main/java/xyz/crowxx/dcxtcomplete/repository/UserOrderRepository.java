package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.UserOrder;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder,Long> {
    @Query(value = "SELECT * FROM user_order WHERE user_id = ?1 AND is_pay = 1",nativeQuery = true)
    List<UserOrder> findOrderByUserIdAndIsPay(Long user_id);
}
