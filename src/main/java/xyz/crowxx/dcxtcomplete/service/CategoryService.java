package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.model.Category;
import xyz.crowxx.dcxtcomplete.model.Food;
import xyz.crowxx.dcxtcomplete.repository.CategoryRepository;
import xyz.crowxx.dcxtcomplete.repository.FoodRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Resource
    CategoryRepository categoryRepository;

    @Resource
    FoodRepository foodRepository;

    @Resource
    AppStorageService appStorageService;
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long cid) {
        List<Food> foods = foodRepository.findFoodsByCid(cid);

        if(foods.size()!=0){
            for (Food food : foods) {
                String fileName = food.getImage_url().replace("images/","");
                Boolean flag = appStorageService.deleteFileByFileName(fileName);
                System.out.println(fileName + "---------------" + flag);
            }
        }
        foodRepository.deleteFoodsByCid(cid);
        categoryRepository.deleteById(cid);
    }

    public List<Category> findNotId(Long id) {
        return categoryRepository.findNotId(id);
    }
}
