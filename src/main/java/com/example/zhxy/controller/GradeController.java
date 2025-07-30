package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.pojo.Grade;
import com.example.zhxy.service.GradeService;
import com.example.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    /**gradeName似乎没有用？
     * 查询年级信息，带分页条件
     * @param pagination
     * @param pageSize
     * @param gradeName
     * @return
     */
    @ApiOperation("查询年级信息，带分页条件")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradeByOpr(
            @ApiParam("分页查询页码数") @PathVariable(value="pageNo") Integer pagination,//页码
            @ApiParam("分页查询页大小") @PathVariable(value="pageSize") Integer pageSize,//页大小
            @ApiParam("分页查询模糊匹配班级名") String gradeName//模糊查询条件
    ){
        //设置分页信息
        Page<Grade> pageParam= new Page<>(pagination,pageSize);
        //调用服务层方法
        IPage<Grade> pageRs = gradeService.getGradeByOpr(pageParam, gradeName);
        return Result.ok(pageRs);
    }
}
