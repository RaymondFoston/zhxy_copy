package com.example.zhxy.controller;

import com.example.zhxy.pojo.Admin;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.service.AdminService;
import com.example.zhxy.service.StudentService;
import com.example.zhxy.service.TeacherService;
import com.example.zhxy.util.CreateVerifiCodeImage;
import com.example.zhxy.util.JwtHelper;
import com.example.zhxy.util.Result;
import com.example.zhxy.util.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码功能
 */

@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //1.获取验证码字符串
        String verifiCode = CreateVerifiCodeImage.getVerifiCode();
        //2.获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //将验证码放入当前请求域
        request.getSession().setAttribute("verifiCode", verifiCode);
        //将验证码图片通过输出流做出响应
        try {
            ImageIO.write(verifiCodeImage,"jpeg",response.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 登录请求验证
     * @return
     */
    @ApiOperation("登陆请求验证")
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){
        //获取用户提交的验证码和session域中的验证码
        HttpSession session = request.getSession();
        String systemVerifiCode = (String)session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        //session过期，验证码超时
        if ("".equals(systemVerifiCode)){
           return Result.fail().message("验证码超时，请刷新后重试");
        }
        //验证码有误
        if (!loginVerifiCode.equalsIgnoreCase(systemVerifiCode)){
            return Result.fail().message("验证码有误，请刷新后重新输入");
        }
        //验证码使用完毕，移除当前请求域中的验证码
        session.removeAttribute("verifiCode");

        //存放用户响应信息
        Map<String, Object> map = new HashMap<>();
        switch (loginForm.getUserType()){
            case 1://管理员用户
                //调用服务层login方法，根据用户提交的logininfo信息，查询对应的admin信息
                Admin login = adminService.login(loginForm);
                //登录成功，将用户id和用户类型转换为token口令，作为信息响应给前端
                if (login != null){
                    map.put("token", JwtHelper.createToken(login.getId().longValue(),0));
                }else{
                    throw new RuntimeException("用户名或密码错误");
                }
                return Result.ok(map);
        }
        return Result.fail().message("查无此用户");
    }
    @ApiOperation("通过token获取用户信息")
    @GetMapping("/getInfo")
    public Result getUserInfoByToken(HttpServletRequest request,
                                     //获取用户请求中token
                                     @RequestHeader("token")String token){
        //检查token是否过期 20h
        boolean isExpiration = JwtHelper.isExpiration(token);
        if (isExpiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //解析tokan，获取用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //创建一个map对象，用于存放响应数据
        HashMap<String, Object> map= new HashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminByid(userId.intValue());
                map.put("user",admin);
                map.put("userType",1);
                break;
        }
        return Result.ok(map);
    }


}
