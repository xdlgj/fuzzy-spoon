package org.xdl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xdl.reggie.common.R;
import org.xdl.reggie.entity.Dish;
import org.xdl.reggie.service.DishService;

@RequestMapping("/dish")
@RestController
@Slf4j
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;
    /**
     * 分页查询菜品
     * @param page
     * @param pageSize
     * @param name 菜品名称
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器对象
        Page<Dish> pageInfo = new Page(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }
}
