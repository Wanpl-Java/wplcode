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
public class GymSubmission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer topicId;
    private String topicName;
    private String language;
    private String result;
    private Integer timeCost;
    private Integer memoryCost;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date submitTime;
}
