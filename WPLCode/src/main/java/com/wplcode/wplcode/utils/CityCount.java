package com.wplcode.wplcode.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityCount {
    private Integer rank;
    private String cityName;
    private Integer count;
}
