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
    @Query(value = "select * from food where category_id = ?1 and status = ?2",nativeQuery = true)
    List<Food> findFoodsByCidAndStatus(Long id, Integer status);

    @Query(value = "SELECT COUNT(*) FROM food",nativeQuery = true)
    int getLineCount();

    @Query(value = "SELECT * FROM food ORDER BY category_id LIMIT ?1,?2",nativeQuery = true)
    List<Food> findFoodsByPage(int pageNow, int pageSize);

    @Query(value = "select * from food where category_id = ?1 and if(?2 !='',name like concat('%',?2,'%'),1=1) order by category_id limit ?3,?4",nativeQuery = true)
    List<Food> findFoodsByPageAndCondition(Long categoryid, String foodName, int pageNow, int pageSize);
    @Query(value = "select * from food where if(?1 !='',name like concat('%',?1,'%'),1=1) order by category_id limit ?2,?3",nativeQuery = true)
    List<Food> findFoodsByPageAndNameLike(String foodName, int pageNow, int pageSize);
}
