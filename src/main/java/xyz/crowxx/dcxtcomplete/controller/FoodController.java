package xyz.crowxx.dcxtcomplete.controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import xyz.crowxx.dcxtcomplete.VO.ShowFoodVO;
import xyz.crowxx.dcxtcomplete.model.Category;
import xyz.crowxx.dcxtcomplete.model.Food;
import xyz.crowxx.dcxtcomplete.service.AppStorageService;
import xyz.crowxx.dcxtcomplete.service.CategoryService;
import xyz.crowxx.dcxtcomplete.service.FoodService;
import xyz.crowxx.dcxtcomplete.util.DateUtil;
import xyz.crowxx.dcxtcomplete.util.IsNumberUtil;
import xyz.crowxx.dcxtcomplete.util.SortList;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/food")
public class FoodController {
    @Resource
    FoodService foodService;
    @Resource
    CategoryService categoryService;
    @Resource
    AppStorageService appStorageService;

    @GetMapping("/getfoodaddindex")
    public String getFoodIndex(Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories.size() != 0) {
            Collections.sort(categories, new SortList<Category>("sort", true));
            model.addAttribute("categories", categories);
        } else {
            Category category = new Category();
            category.setSort(1);
            category.setName("空类型");
            Category category1 = categoryService.addCategory(category);
            categories.add(category1);
            model.addAttribute("categories", categories);
        }
        return "admin/food_add/index";
    }

    @PostMapping("/addfood")
    public String addFood(HttpServletResponse response, @RequestParam("category_id") Long category_id, @RequestParam("name") String name, @RequestParam("price") String pricestr, @RequestParam("file") MultipartFile file, @RequestParam("status") Integer status) throws IOException {
        System.out.println("-----------" + status + "--------------------");
        String tmp = pricestr.replace(".", "");

        Boolean flag = IsNumberUtil.isNumber(tmp);

        if (flag) {
            String filename = "images/" + appStorageService.store(file);
            BigDecimal price = new BigDecimal(pricestr);
            Food food = new Food();
            food.setCategory_id(category_id);
            food.setCreate_time(DateUtil.getDate());
            food.setImage_url(filename);
            food.setName(name);
            food.setPrice(price);
            food.setStatus(status);
            foodService.addFood(food);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('添加成功');window.location.href='/admin/food/getfoodaddindex'</script>");
            return "admin/food_add/index";
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('添加失败');window.location.href='/admin/food/getfoodaddindex'</script>");
            return "admin/food_add/index";
        }


    }

    @GetMapping("/getfoodmanageindex")
    public String getFoodManageIndex(Model model) {
        List<Category> categories = categoryService.findAll();
        Collections.sort(categories, new SortList<Category>("sort", true));
        ;
        List<Food> foods = foodService.findAll();
        List<ShowFoodVO> foodVOS = new ArrayList<>();
        for (Food food : foods) {
            ShowFoodVO foodVO = new ShowFoodVO();
            foodVO.setId(food.getId());
            foodVO.setName(food.getName());
            foodVO.setCreate_time(food.getCreate_time());
            foodVO.setImgUrl(food.getImage_url());
            String status = food.getStatus() == 0 ? "下架" : "上架";
            foodVO.setStatus(status);
            foodVO.setPrice(food.getPrice());
            Optional<Category> category = categoryService.findCategoryById(food.getCategory_id());
            foodVO.setCategory(category.get().getName());
            foodVO.setCategory_id(food.getCategory_id());
            foodVOS.add(foodVO);
        }
        Collections.sort(foodVOS, new SortList<ShowFoodVO>("category_id", true));
        model.addAttribute("foods", foodVOS);
        model.addAttribute("categories", categories);
        return "admin/food_manage/index";
    }

    @GetMapping("/findfoods")
    public String findFoods(Model model, Long categoryid, String search) {
        List<Category> categories = categoryService.findAll();
        Collections.sort(categories, new SortList<Category>("sort", true));
        ;
        List<ShowFoodVO> foodVOS = new ArrayList<>();
        if (categoryid == -1) {
            String foodName = search;
            List<Food> foods = foodService.findFoodByFoodNameLike(search);

            for (Food food : foods) {
                ShowFoodVO foodVO = new ShowFoodVO();
                foodVO.setId(food.getId());
                foodVO.setName(food.getName());
                foodVO.setCreate_time(food.getCreate_time());
                String status = food.getStatus() == 0 ? "下架" : "上架";
                foodVO.setStatus(status);
                foodVO.setPrice(food.getPrice());
                Optional<Category> category = categoryService.findCategoryById(food.getCategory_id());
                foodVO.setCategory(category.get().getName());
                foodVO.setCategory_id(food.getCategory_id());
                foodVOS.add(foodVO);
            }
            Collections.sort(foodVOS, new SortList<ShowFoodVO>("category_id", true));
        } else {
            String foodName = search;
            List<Food> foods = foodService.findFoodByCondition(categoryid, foodName);
            for (Food food : foods) {
                ShowFoodVO foodVO = new ShowFoodVO();
                foodVO.setId(food.getId());
                foodVO.setName(food.getName());
                foodVO.setCreate_time(food.getCreate_time());
                String status = food.getStatus() == 0 ? "下架" : "上架";
                foodVO.setStatus(status);
                foodVO.setPrice(food.getPrice());
                Optional<Category> category = categoryService.findCategoryById(food.getCategory_id());
                foodVO.setCategory(category.get().getName());
                foodVO.setCategory_id(food.getCategory_id());
                foodVOS.add(foodVO);
            }
            Collections.sort(foodVOS, new SortList<ShowFoodVO>("category_id", true));
        }
        model.addAttribute("foods", foodVOS);
        model.addAttribute("categories", categories);
        return "admin/food_manage/index";
    }

    @GetMapping("/deletefood")
    public String deleteFood(Long id, HttpServletResponse response) throws IOException {
        foodService.deleteFoodById(id);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('删除成功');window.location.href='/admin/food/getfoodmanageindex'</script>");

        return "admin/food_manage/index";
    }

    @GetMapping("/geteditindex")
    public String getEditIndex(Model model, Long id,Long cid) {
        Optional<Food> foodOptional = foodService.findFoodById(id);
        Food food = foodOptional.get();
        Optional<Category> categoryOptional = categoryService.findCategoryById(food.getCategory_id());
        Category category = categoryOptional.get();
        List<Category> categories = categoryService.findNotId(cid);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        for (Category category1 : categories) {
            categoryList.add(category1);
        }
        String url = "/static/static/uploads/" + food.getImage_url();
        food.setImage_url(url);
        model.addAttribute("food", food);
        model.addAttribute("categories", categoryList);
        return "admin/food_manage/edit";
    }

    @PostMapping("/updatefood")
    public String updateFood(Model model,HttpServletResponse response, @RequestParam("foodid") Long foodid, @RequestParam("category_id")Long category_id, @RequestParam("name") String name, @RequestParam("price") String pricestr, @RequestParam("file")MultipartFile file, @RequestParam("status") Integer status) throws IOException {
        String tmp = pricestr.replace(".", "");
        Boolean flag = IsNumberUtil.isNumber(tmp);
        if (flag) {
            Optional<Food> foodOptional = foodService.findFoodById(foodid);
            Food food = foodOptional.get();
            String orImgUrl = food.getImage_url().replace("images/","");
            BigDecimal price = new BigDecimal(pricestr);
            if (StringUtils.isNotBlank(file.getOriginalFilename())) {
                appStorageService.deleteFileByFileName(orImgUrl);
                String newUrl = "images/" + appStorageService.store(file);
                food.setImage_url(newUrl);
                food.setStatus(status);
                food.setPrice(price);
                food.setName(name);
                food.setCategory_id(category_id);
                foodService.addFood(food);
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print("<script language=\"javascript\">alert('修改成功');window.location.href='/admin/food/getfoodmanageindex'</script>");
                return "admin/food_manage/index";
            }else {
                food.setStatus(status);
                food.setPrice(price);
                food.setName(name);
                food.setCategory_id(category_id);
                foodService.addFood(food);
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print("<script language=\"javascript\">alert('修改成功');window.location.href='/admin/food/getfoodmanageindex'</script>");
                return "admin/food_manage/index";
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('修改失败');window.location.href='/admin/food/getfoodmanageindex'</script>");
            return "admin/food_manage/index";
        }
    }
}
