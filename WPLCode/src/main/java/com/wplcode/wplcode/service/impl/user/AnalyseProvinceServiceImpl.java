package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.AnalyseProvinceService;
import com.wplcode.wplcode.utils.ProvinceCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyseProvinceServiceImpl implements AnalyseProvinceService {

    private final UserMapper userMapper;

    @Override
    public List<ProvinceCount> getProvinceCount() {
        List<ProvinceCount> resp = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        int rank = 1;
        Map<String, Integer> map = new HashMap<>();
        for (User user : userMapper.selectList(queryWrapper)) {
            String provinceName = user.getProvince();
            if (!map.containsKey(provinceName)) {
                map.put(provinceName, 1);
            } else {
                map.put(provinceName, map.get(provinceName) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String provinceName = entry.getKey();
            Integer count = entry.getValue();
            ProvinceCount provinceCount = new ProvinceCount(
                    0,
                    provinceName,
                    count
            );
            resp.add(provinceCount);
        }
        // 根据count从大到小排序
        resp.sort((o1, o2) -> o2.getCount() - o1.getCount());
        for (ProvinceCount provinceCount : resp) {
            provinceCount.setRank(rank ++ );
        }
        return resp;
    }
}
