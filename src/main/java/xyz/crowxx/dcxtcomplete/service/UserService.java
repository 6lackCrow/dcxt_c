package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.model.User;
import xyz.crowxx.dcxtcomplete.repository.UserRepository;
import xyz.crowxx.dcxtcomplete.util.DateUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updataUser(Long user_id, BigDecimal price) {
        Optional<User> userOptional = userRepository.findById(user_id);
        User user = userOptional.get();
        user.setPrice(user.getPrice().add(price));
        return userRepository.save(user);
    }

    public List<User> findAllUserAndPage(int pageNow, int pageSize) {
        return userRepository.findAllUserAndPage(pageNow,pageSize);
    }
}
