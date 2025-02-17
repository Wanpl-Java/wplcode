package com.wplcode.wplcode.pojo.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContestRank {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer contestId;
    private Integer userId;
    private Integer penalty;
    private String topicA;
    private String topicB;
    private String topicC;
    private String topicD;
    private String topicE;
}
