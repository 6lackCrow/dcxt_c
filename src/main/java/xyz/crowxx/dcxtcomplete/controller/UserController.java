package xyz.crowxx.dcxtcomplete.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.crowxx.dcxtcomplete.model.User;
import xyz.crowxx.dcxtcomplete.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Resource
    UserService userService;
    @GetMapping("/index")
    public String getUserList(Model model,Integer pageNow){
        System.out.println("users"+pageNow);
        /*分页：pageSize：一页显示多少数据
         * lineCount：一共有多少行数据
         * pageCount：需要分多少页
         * pageNow：用户当前访问第几页*/
        List<User> list = userService.findAll();
        int pageSize = 10;
        int lineCount = list.size();
        int pageCount = lineCount % pageSize ==0 ? lineCount/pageSize :  (lineCount/pageSize)+1;
        int[] pageList = new int[pageCount];
        for (int i = 0; i < pageCount; i++) {
            pageList[i] = i+1;
        }
        List<User> users = userService.findAllUserAndPage(pageSize*(pageNow-1),pageSize);
        model.addAttribute("users",users)                    .addAttribute("pageNow",pageNow)
                .addAttribute("pageBack",pageNow-1)
                .addAttribute("pageNext",pageNow+1)
                .addAttribute("pageMax",pageCount)
                .addAttribute("pageList",pageList);
        return "admin/user/index";
    }
}
