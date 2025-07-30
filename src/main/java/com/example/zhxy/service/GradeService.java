package com.example.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.pojo.Grade;

public interface GradeService extends IService<Grade> {
    /**
     * 查询年纪信息，分页带条件
     * @param pageParam
     * @param gradeName
     * @return
     */
    IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName);
}
