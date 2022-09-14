package org.xdl.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xdl.reggie.entity.Employee;
import org.xdl.reggie.mapper.EmployeeMapper;
import org.xdl.reggie.service.EmployeeService;

@Service  // 交给spring管理
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
