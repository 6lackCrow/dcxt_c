package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import xyz.crowxx.dcxtcomplete.model.Setting;

import javax.transaction.Transactional;

public interface SettingRepository extends JpaRepository<Setting,Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO setting (NAME,VALUE) VALUES ('appid',''),('appsecret',''),('img_ad','/static/uploads/default/image_ad.png'),('img_category','[\"\\/static\\/uploads\\/default\\/bottom_1.png\",\"\\/static\\/uploads\\/default\\/bottom_2.png\",\"\\/static\\/uploads\\/default\\/bottom_3.png\",\"\\/static\\/uploads\\/default\\/bottom_1.png\"]'),('img_swiper','[\"\\/static\\/uploads\\/default\\/banner_1.png\",\"\\/static\\/uploads\\/default\\/banner_2.png\",\"\\/static\\/uploads\\/default\\/banner_3.png\"]'),('promotion','[{\"k\":50,\"v\":10}]')",nativeQuery = true)
    void setDefaultName();

    Setting findSettingByName(String name);
}
