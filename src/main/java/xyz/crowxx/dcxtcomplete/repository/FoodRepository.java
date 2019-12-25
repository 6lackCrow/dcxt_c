package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.Food;

import javax.transaction.Transactional;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM food WHERE category_id = ?1",nativeQuery = true)
    void deleteFoodsByCid(Long id);
    @Query(value = "SELECT * FROM food WHERE category_id = ?1",nativeQuery = true)
    List<Food> findFoodsByCid(Long cid);
    @Query(value =  "select * from food where category_id = ?1 and if(?2 !='',name like concat('%',?2,'%'),1=1)",nativeQuery = true)
    List<Food> findFoodByCondition(Long categoryid, String foodName);
    @Query(value = "select * from food where if(?1 !='',name like concat('%',?1,'%'),1=1) ",nativeQuery = true)
    List<Food> findFoodByFoodNameLike(String search);
}
