package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByOpenid(String openid);
    @Query(value = "select * from user limit ?1,?2",nativeQuery = true)
    List<User> findAllUserAndPage(int pageNow, int pageSize);
}
