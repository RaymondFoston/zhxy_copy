package com.example.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.pojo.Admin;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.pojo.Student;
import org.springframework.stereotype.Service;


public interface StudentService extends IService<Student> {
    /**
     * login
     * @param loginForm
     * @return
     */
    Student login(LoginForm loginForm);

    /**
     * query student
     * @param id
     * @return
     */
    Student getStudentByid(int id);
}
