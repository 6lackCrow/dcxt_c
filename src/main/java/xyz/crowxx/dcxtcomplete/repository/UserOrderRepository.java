package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.crowxx.dcxtcomplete.model.UserOrder;

public interface UserOrderRepository extends JpaRepository<UserOrder,Long> {

}
