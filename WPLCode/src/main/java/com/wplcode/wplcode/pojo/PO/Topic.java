package com.wplcode.wplcode.pojo.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String source;
    private String algorithm;
    private String difficulty;
    private String content;
    private String inputFormat;
    private String outputFormat;
    private String dataRange;
    private String inputExample;
    private String outputExample;
}
