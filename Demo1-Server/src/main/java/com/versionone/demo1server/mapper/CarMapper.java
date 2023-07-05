package com.versionone.demo1server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.versionone.demo1server.object.entity.Car;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据库Car表的读写接口
 */
@Mapper
public interface CarMapper extends BaseMapper<Car> {

}
