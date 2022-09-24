package org.xdl.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xdl.reggie.entity.Category;
import org.xdl.reggie.mapper.CategoryMapper;
import org.xdl.reggie.service.CategoryService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
