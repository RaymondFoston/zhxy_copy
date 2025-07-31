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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    /**
     * 查询年级信息，带分页条件
     * @param pagination
     * @param pageSize
     * @param gradeName 这个是不是没有用？
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

    @ApiOperation("获取所有Grade信息")
    @GetMapping("/getGrades")
    public Result getGrades(){
       List<Grade> grades = gradeService.getGrades();
       return Result.ok(grades);
    }

    /**
     * 添加或修改年级信息
     * @param grade
     * @return
     */
    @ApiOperation("添加或修改年级信息")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("JSON的grade对象转换后台数据模型")@RequestBody Grade grade){
        //调用服务层方法，实现添加或修改年纪信息
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    /**
     * 删除一个或多个grade信息
     * @param ids
     * @return
     */
    @ApiOperation("删除一个或多个grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deletGradeById(@ApiParam("JSON的年级id集合，映射为后台List<Integer>")@RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return Result.ok();
    }
}
