package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryByName(String name);
    @Query(value = "select * from category where id != ?1",nativeQuery = true)
    List<Category> findNotId(Long id);
}
