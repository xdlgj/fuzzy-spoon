package org.xdl.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xdl.reggie.entity.Employee;

@Mapper  // 相当于在主类或mybatis配置类上@MapperScan("org.xdl.reggie.mapper")
public interface EmployeeMapper extends BaseMapper<Employee> {
}
