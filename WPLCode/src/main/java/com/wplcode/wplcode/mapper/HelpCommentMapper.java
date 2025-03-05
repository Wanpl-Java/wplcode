package com.wplcode.wplcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wplcode.wplcode.pojo.PO.HelpComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HelpCommentMapper extends BaseMapper<HelpComment> {

    @Update("update help_comment set likes = #{likes} where id = #{id}")
    public void updateLikes(Integer likes, Integer id);

    @Update("update help_comment set dislikes = #{dislikes} where id = #{id}")
    public void updateDislikes(Integer dislikes, Integer id);

    @Update("update help_comment set likes_lst = #{likes_lst} where id = #{id}")
    public void updateLikesLst(String likes_lst, Integer id);

    @Update("update help_comment set dislikes_lst = #{dislikes_lst} where id = #{id}")
    public void updateDislikesLst(String dislikes_lst, Integer id);
}
