package com.example.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.pojo.Admin;
import com.example.zhxy.pojo.LoginForm;
import org.springframework.stereotype.Service;


public interface AdminService extends IService<Admin> {
    /**
     * 登录
     * @param loginForm
     * @return
     */
    Admin login(LoginForm loginForm);

    /**
     * 查询用户
     * @param id
     * @return
     */
    Admin getAdminByid(int id);
}
