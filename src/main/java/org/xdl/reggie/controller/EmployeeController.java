package org.xdl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xdl.reggie.common.R;
import org.xdl.reggie.entity.Employee;
import org.xdl.reggie.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
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
}
