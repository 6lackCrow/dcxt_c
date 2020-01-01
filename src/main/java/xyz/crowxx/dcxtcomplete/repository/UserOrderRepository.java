package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.UserOrder;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder,Long> {
    @Query(value = "SELECT * FROM user_order WHERE user_id = ?1 AND is_pay = 1",nativeQuery = true)
    List<UserOrder> findOrderByUserIdAndIsPay(Long user_id);
    @Query(value = "select * from user_order where user_id = ?1 and is_pay=1 order by ?1 desc limit ?2,?3",nativeQuery = true)
    List<UserOrder> findOrderByUserIdAndIsPayAndLimit(Long id, int last_id, int row);
    @Query(value = "select * from user_order where is_pay = 1 and is_taken = 0",nativeQuery = true)
    List<UserOrder> findOrderIsPayAndNotTaken();
    @Query(value = "select * from user_order where is_pay = 1",nativeQuery = true)
    List<UserOrder> findOrderIsPay();
    @Query(value = "select * from user_order where is_taken = 1",nativeQuery = true)
    List<UserOrder> findOrderIsTaken();
    @Query(value = "select * from user_order where is_pay = 0",nativeQuery = true)
    List<UserOrder> findOrderIsNotPay();
    @Query(value = "select * from user_order order by id desc limit ?1,?2",nativeQuery = true)
    List<UserOrder> findAllOrderByPage(int pageNow, int pageSize);
    @Query(value = "select * from user_order where is_pay = 1 and is_taken = 0 order by id desc limit ?1,?2",nativeQuery = true)
    List<UserOrder> findOrderIsPayAndNotTakenByPage(int pageNow, int pageSize);
    @Query(value = "select * from user_order where is_pay = 1 order by id desc limit ?1,?2",nativeQuery = true)
    List<UserOrder> findOrderIsPayByPage(int pageNow, int pageSize);
    @Query(value = "select * from user_order where is_taken = 1 order by id desc limit ?1,?2",nativeQuery = true)
    List<UserOrder> findOrderIsTakenByPage(int pageNow, int pageSize);
    @Query(value = "select * from user_order where is_pay = 0 order by id desc limit ?1,?2",nativeQuery = true)
    List<UserOrder> findOrderIsNotPayByPage(int pageNow, int pageSize);

}
