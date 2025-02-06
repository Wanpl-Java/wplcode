package com.wplcode.wplcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wplcode.wplcode.pojo.PO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update user set password=#{password} where id = #{id}")
    void updatePassword(@Param("password") String password, @Param("id") Integer id);
    @Update("update user set username=#{username}, province=#{province}, city=#{city} where id = #{id}")
    void updateSettings(@Param("username") String username, @Param("province") String province, @Param("city") String city, @Param("id") Integer id);
    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username") String username);
}
