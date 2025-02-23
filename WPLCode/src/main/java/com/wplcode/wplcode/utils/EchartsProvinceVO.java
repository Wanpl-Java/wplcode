package com.wplcode.wplcode.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EchartsProvinceVO {
    private Integer count; // 地区注册人数
    private Long sumRating; // 地区rating总分
    private Double averageRating; // 地区平均rating分
}
