package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.AnalyseCityService;
import com.wplcode.wplcode.utils.CityCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyseCityServiceImpl implements AnalyseCityService {

    private final UserMapper userMapper;

    @Override
    public List<CityCount> analyseCity() {
        List<CityCount> resp = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        int rank = 1;
        Map<String, Integer> map = new HashMap<>();
        for (User user : userMapper.selectList(queryWrapper)) {
            String cityName = user.getCity();
            if (!map.containsKey(cityName)) {
                map.put(cityName, 1);
            } else {
                map.put(cityName, map.get(cityName) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String cityName = entry.getKey();
            Integer count = entry.getValue();
            CityCount cityCount = new CityCount(
                    0,
                    cityName,
                    count
            );
            resp.add(cityCount);
        }
        // 根据count从大到小排序
        resp.sort((o1, o2) -> o2.getCount() - o1.getCount());
        for (CityCount cityCount : resp) {
            cityCount.setRank(rank ++ );
        }
        return resp;
    }
}
