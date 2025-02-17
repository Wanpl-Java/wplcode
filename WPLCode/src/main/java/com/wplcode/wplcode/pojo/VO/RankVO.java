package com.wplcode.wplcode.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankVO {
    private Integer rank;
    private String username;
    private Integer penalty;
    private Integer passCounts;
    private String topicA;
    private String topicB;
    private String topicC;
    private String topicD;
    private String topicE;
    private Integer rating;
}
