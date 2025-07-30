package com.example.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.pojo.Student;
import com.example.zhxy.pojo.Teacher;
import org.springframework.stereotype.Service;


public interface TeacherService extends IService<Teacher> {
    /**
     * Login
     * @param loginForm
     * @return
     */
    Teacher login(LoginForm loginForm);

    /**
     * Query Teacher
     * @param id
     * @return
     */
    Teacher getTeacherByid(int id);
}
