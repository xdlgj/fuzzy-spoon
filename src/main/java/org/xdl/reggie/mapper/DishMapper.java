package org.xdl.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xdl.reggie.entity.Dish;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
