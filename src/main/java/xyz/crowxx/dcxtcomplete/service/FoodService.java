package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.model.Food;
import xyz.crowxx.dcxtcomplete.repository.CategoryRepository;
import xyz.crowxx.dcxtcomplete.repository.FoodRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    @Resource
    FoodRepository foodRepository;

    @Resource
    CategoryRepository categoryRepository;

    @Resource
    AppStorageService appStorageService;
    public void addFood(Food food) {
        foodRepository.save(food);
    }

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public List<Food> findFoodByCondition(Long categoryid, String foodName) {
        return foodRepository.findFoodByCondition(categoryid,foodName);
    }

    public List<Food> findFoodByFoodNameLike(String search) {
        return foodRepository.findFoodByFoodNameLike(search);
    }

    public Optional<Food> findFoodById(Long id) {
        return foodRepository.findById(id);
    }

    public void deleteFoodById(Long id) {
        Optional<Food> foodOptional = foodRepository.findById(id);
        String imgPath = foodOptional.get().getImage_url().replace("images/","");
        appStorageService.deleteFileByFileName(imgPath);
        foodRepository.deleteById(id);
    }
}
