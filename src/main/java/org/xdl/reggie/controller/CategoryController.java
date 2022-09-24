package org.xdl.reggie.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xdl.reggie.common.R;
import org.xdl.reggie.entity.Category;
import org.xdl.reggie.service.CategoryService;

/**
 * @RequiredArgsConstructor可以将类中final修饰的或@NoNull修饰的属性，作为参数生成构造方法，
 * 可以代替@Autowired进行依赖注入
 * public CategoryController(final CategoryService categoryService) {
 *         this.categoryService = categoryService;
 * }
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("添加分类：{}", category.toString());
        categoryService.save(category);
        return R.success("新增分类成功");

    }
}
