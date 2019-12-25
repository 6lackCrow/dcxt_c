package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.model.User;
import xyz.crowxx.dcxtcomplete.repository.UserRepository;
import xyz.crowxx.dcxtcomplete.util.DateUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class UserService {
    @Resource
    UserRepository userRepository;
    public User findUserByOpenID(String openid) {
        User user = userRepository.findUserByOpenid(openid);
        return user;
    }

    public User createUser(String openid) {
        User user = new User();
        user.setOpenid(openid);
        user.setCreate_time(DateUtil.getDate());
        user.setPrice(new BigDecimal(0.00));
        return userRepository.save(user);
    }
}
