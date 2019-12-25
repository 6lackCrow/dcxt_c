package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.model.Setting;
import xyz.crowxx.dcxtcomplete.repository.SettingRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class SettingService {
    @Resource
    SettingRepository settingRepository;
    public Boolean updateSetting(List<String> updateList) {
        List<Setting> list = settingRepository.findAll();
        if (list.size()!=0){
            for (int i = 0; i < list.size(); i++) {
                Setting setting = list.get(i);
                setting.setValue(updateList.get(i));
                settingRepository.save(setting);
            }
            return true;
        }
        return false;
    }

    public String findAppID() {
        Optional<Setting> setting = Optional.ofNullable(settingRepository.findSettingByName("appid"));
        return setting.get().getValue();
    }

    public String findAppSecret() {
        Setting setting = settingRepository.findSettingByName("appsecret");
        return setting.getValue();
    }

    public Setting findSettingByName(String name) {
        return settingRepository.findSettingByName(name);
    }
}
