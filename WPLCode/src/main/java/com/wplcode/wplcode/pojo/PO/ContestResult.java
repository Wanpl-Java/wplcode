package com.wplcode.wplcode.pojo.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContestResult {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer contestId;
    private Integer userId;
    private String result;
    private Integer penalty;
    private Integer ratingChange;
}
