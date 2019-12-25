package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.crowxx.dcxtcomplete.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByOpenid(String openid);
}
