package xyz.crowxx.dcxtcomplete.service;

import org.springframework.stereotype.Service;
import xyz.crowxx.dcxtcomplete.core.exception.NotFoundException;
import xyz.crowxx.dcxtcomplete.model.Admin;
import xyz.crowxx.dcxtcomplete.model.Setting;
import xyz.crowxx.dcxtcomplete.repository.AdminRepository;
import xyz.crowxx.dcxtcomplete.repository.SettingRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Resource
    AdminRepository adminRepository;
    @Resource
    SettingRepository settingRepository;
    public Boolean login(String username, String password, HttpServletRequest request) {
        Optional<Admin> adminOptional = Optional.ofNullable(adminRepository.findAdminByUsername(username));
        if (adminOptional.isEmpty()){
            return false;
        }
        Admin admin = adminOptional.get();
        if(admin.getPassword().equals(password)){
            HttpSession session = request.getSession();
            session.setAttribute("admin",admin);
            return true;
        }
        return false;
    }

    public Boolean isAlreadyExistsl() {
        List<Admin> list = adminRepository.findAll();
        if(list.size()!=0) return true;
        return false;
    }

    public void setAdmin(String admin_username, String admin_pwd1) {
        Admin admin = new Admin();
        admin.setUsername(admin_username);
        admin.setPassword(admin_pwd1);
        adminRepository.save(admin);
        List<Setting> list = settingRepository.findAll();
        if (list.size()==0){
            settingRepository.setDefaultName();
        }
    }
}
