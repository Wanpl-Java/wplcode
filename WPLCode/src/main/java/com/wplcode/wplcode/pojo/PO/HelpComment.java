package com.wplcode.wplcode.pojo.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpComment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String creator;
    private String creatorPhoto;
    private Integer creatorRating;
    private String content;
    private Integer likes;
    private Integer dislikes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    private String likesLst;
    private String dislikesLst;
}
