package org.xdl.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xdl.reggie.entity.Dish;
import org.xdl.reggie.mapper.DishMapper;
import org.xdl.reggie.service.DishService;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
