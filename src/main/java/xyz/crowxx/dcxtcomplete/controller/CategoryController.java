package xyz.crowxx.dcxtcomplete.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.crowxx.dcxtcomplete.model.Category;
import xyz.crowxx.dcxtcomplete.service.CategoryService;
import xyz.crowxx.dcxtcomplete.util.IsNumberUtil;
import xyz.crowxx.dcxtcomplete.util.SortList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Resource
    CategoryService categoryService;
    @GetMapping("/index")
    public String getCategoryIndex(Model model){
        List<Category> list = categoryService.findAll();
        Collections.sort(list, new SortList<Category>("sort",true));;
        model.addAttribute("categories",list);
        return "admin/category/index";
    }

    @PostMapping("/addcategory")
    public String addCategory(AddCategoryParm parm){
        Category category = new Category();
        category.setName(parm.name);
        String paraSort = parm.sort;
        int sort = 0;
        if (IsNumberUtil.isNumber(paraSort)){
            sort = Integer.parseInt(paraSort);
            if (sort<0){
                sort = 0;
            }
        }
        category.setSort(sort);
        categoryService.addCategory(category);
        return "redirect:/admin/category/index";
    }

    @PostMapping("/updatesort")
    public String updateSort(Long id,String sort){
        Optional<Category> categoryOptional = categoryService.findCategoryById(id);
        Category category = categoryOptional.get();
        int tmp = 0;
        if (IsNumberUtil.isNumber(sort)){
            tmp = Integer.parseInt(sort);
            if (tmp<0){
                tmp = 0;
            }
        }
        category.setSort(tmp);
        categoryService.addCategory(category);
        return "redirect:/admin/category/index";
    }

    @PostMapping("/updatecategoryname")
    public String updateCategoryName(Long id,String category_name){
        System.out.println(id + " " + category_name);
        Optional<Category> categoryOptional = categoryService.findCategoryById(id);
        Category category = categoryOptional.get();
        if (!category_name.equals("")){
            category.setName(category_name);
        }
        categoryService.addCategory(category);
        return "redirect:/admin/category/index";
    }
    @GetMapping("/deletecategory")
    public String deleteCategory(Long id, HttpServletResponse response) throws IOException {
        categoryService.deleteById(id);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('删除');window.location.href='/admin/category/index'</script>");
        return "redirect:/admin/category/index";
    }
}
@Data
class AddCategoryParm{
    String sort;
    String name;
}

