package com.wplcode.wplcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wplcode.wplcode.pojo.PO.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
