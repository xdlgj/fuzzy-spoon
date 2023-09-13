package org.xdl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.xdl.reggie.common.R;
import org.xdl.reggie.entity.Employee;
import org.xdl.reggie.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "员工管理")
public class EmployeeController {

    @Autowired // 会去Spring容器中查找类型为EmployeeService类型的bean（对象）；new org.xdl.reggie.service.impl.EmployeeServiceImpl()
    private EmployeeService employeeService;

    @ApiOperation(value = "根据ID获取员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工ID")
    })
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应的员工");
    }

    /**
     * 根据id修改员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1、获取密码并进行MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 2、根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        // 3、判断是否存在
        if (emp == null) {
            return R.error("用户不存在");
        }
        // 4、判断密码是否相等
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }
        // 5、查询该员工状态是否可登录
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        // 6、登录成功，将员工ID存入session并返回登录结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清除session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工, 员工信息：{}", employee.toString());

        // 设置初始化密码为123456，需要进行MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        LocalDateTime localDateTime = LocalDateTime.now();
        //employee.setCreateTime(localDateTime);
        //employee.setUpdateTime(localDateTime);
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
}
