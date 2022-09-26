package org.xdl.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xdl.reggie.entity.SetMeal;
import org.xdl.reggie.mapper.SetMealMapper;
import org.xdl.reggie.service.SetMealService;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements SetMealService {
}
