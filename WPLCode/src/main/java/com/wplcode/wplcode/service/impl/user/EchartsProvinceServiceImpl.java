package com.wplcode.wplcode.service.impl.user;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.EchartsProvinceService;
import com.wplcode.wplcode.utils.EchartsProvinceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EchartsProvinceServiceImpl implements EchartsProvinceService {

    private final UserMapper userMapper;

    @Override
    public JSONObject echartsProvince() {
        JSONObject resp = new JSONObject();
        Map<String, EchartsProvinceVO> map = new HashMap<>();
        // map.put("北京", new EchartsProvinceVO(0, 0.0));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            String province = user.getProvince();
            if (map.containsKey(province)) {
                int count = map.get(province).getCount() + 1;
                long sum_rating = (long) user.getRating() + map.get(province).getSumRating();
                double avg_rating = 1.0 * sum_rating / count;
                BigDecimal decimal = new BigDecimal(avg_rating);
                decimal = decimal.setScale(1, RoundingMode.HALF_UP);
                avg_rating = decimal.doubleValue();
                EchartsProvinceVO vo = new EchartsProvinceVO(
                        count,
                        sum_rating,
                        avg_rating
                );
                map.put(province, vo);
            } else {
                double avg_rating = user.getRating();
                BigDecimal decimal = new BigDecimal(avg_rating);
                decimal = decimal.setScale(1, RoundingMode.HALF_UP);
                avg_rating = decimal.doubleValue();
                EchartsProvinceVO vo = new EchartsProvinceVO(
                        1,
                        (long) user.getRating(),
                        avg_rating
                );
                map.put(province, vo);
            }
        }
        resp.set("info", map);
        return resp;
    }
}
