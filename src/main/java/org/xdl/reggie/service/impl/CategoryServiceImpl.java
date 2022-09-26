package org.xdl.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xdl.reggie.common.CustomException;
import org.xdl.reggie.entity.Category;
import org.xdl.reggie.entity.Dish;
import org.xdl.reggie.entity.SetMeal;
import org.xdl.reggie.mapper.CategoryMapper;
import org.xdl.reggie.service.CategoryService;
import org.xdl.reggie.service.DishService;
import org.xdl.reggie.service.SetMealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;
    /**
     * 根据id删除分类，删除之前需要进行判断，是否关联了菜品或套餐
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询当前分类是否关联了菜品或套餐
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            //已经关联了菜品，需要抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品不能删除");
        }
        LambdaQueryWrapper<SetMeal> setMealQueryWrapper = new LambdaQueryWrapper<>();
        setMealQueryWrapper.eq(SetMeal::getCategoryId, id);
        int count1 = setMealService.count(setMealQueryWrapper);
        if (count1 > 0) {
            //已经关联了套餐，需要抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐不能删除");
        }
        super.removeById(id);
    }
}
