package com.wplcode.wplcode.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceCount {
    private Integer rank;
    private String provinceName;
    private Integer count;
}
