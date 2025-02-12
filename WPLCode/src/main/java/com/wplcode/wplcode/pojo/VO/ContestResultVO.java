package com.wplcode.wplcode.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContestResultVO {
    private Integer rank;
    private String username;
    private Integer penalty;
    private List<Integer> results;
    private List<Integer> failedResults;
    private Integer passCounts;
}
