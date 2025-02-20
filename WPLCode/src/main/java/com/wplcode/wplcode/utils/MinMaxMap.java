package com.wplcode.wplcode.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@AllArgsConstructor
public class MinMaxMap {
    private Map<String, Integer> map = new HashMap<>();
    public MinMaxMap() {
        map.put("1010", -5);
        map.put("0101", -5);
        map.put("2020", 5);
        map.put("0202", 5);
        map.put("1012", -1);
        map.put("2101", -1);
        map.put("2021", 1);
        map.put("1202", 1);
        map.put("0110", -5);
        map.put("0220", 5);
        map.put("2110", -1);
        map.put("0112", -1);
        map.put("1220", 1);
        map.put("0221", 1);
        map.put("01110", -500);
        map.put("02220", 500);
        map.put("21110", -100);
        map.put("01112", -100);
        map.put("12220", 100);
        map.put("02221", 100);
        map.put("11010", -500);
        map.put("01011", -500);
        map.put("22020", 500);
        map.put("02022", 500);
        map.put("11012", -100);
        map.put("21011", -100);
        map.put("22021", 100);
        map.put("12022", 100);
        map.put("011110", -50000);
        map.put("022220", 50000);
        map.put("011101", -20000);
        map.put("101110", -20000);
        map.put("022202", 20000);
        map.put("202220", 20000);
        map.put("211101", -10000);
        map.put("101112", -10000);
        map.put("122202", 10000);
        map.put("202221", 10000);
        map.put("011112", -10000);
        map.put("211110", -10000);
        map.put("022221", 10000);
        map.put("122220", 10000);
        map.put("11111", -999999);
        map.put("22222", 999999);
    }
}
