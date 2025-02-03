package com.wplcode.wplcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wplcode.wplcode.pojo.PO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update user set password=#{password} where id = #{id}")
    void updatePassword(@Param("password") String password, @Param("id") Integer id);
}
