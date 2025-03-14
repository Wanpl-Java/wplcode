package com.wplcode.wplcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wplcode.wplcode.pojo.PO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update user set visitors = #{visitors} where username = #{username}")
    void updateVisitors(@Param("visitors") Integer visitors, @Param("username") String username);
    @Update("update user set password=#{password} where id = #{id}")
    void updatePassword(@Param("password") String password, @Param("id") Integer id);
    @Update("update user set username=#{username}, province=#{province}, city=#{city} where id = #{id}")
    void updateSettings(@Param("username") String username, @Param("province") String province, @Param("city") String city, @Param("id") Integer id);
    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username") String username);
    @Update("update user set friends = #{friends}, friend_list = #{friend_list} where id = #{id}")
    void updateFriends(@Param("friends") Integer friends, @Param("friend_list") String friend_list, @Param("id") Integer id);

}
