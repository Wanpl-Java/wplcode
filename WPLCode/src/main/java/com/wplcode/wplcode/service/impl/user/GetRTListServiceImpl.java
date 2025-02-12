package com.wplcode.wplcode.service.impl.user;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.GetRTListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRTListServiceImpl implements GetRTListService {

    private final UserMapper userMapper;

    @Override
    public JSONObject getRTList(String username) {
        JSONObject resp = new JSONObject();
        User user = userMapper.findByUsername(username);
        List<Integer> ratingList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        String rating_list = user.getRatingList();
        String time_list = user.getTimeList();
        for (String str : rating_list.split(",")) {
            ratingList.add(Integer.parseInt(str));
        }
        for (String str : time_list.split(",")) {
            timeList.add(str);
        }
        resp.put("rating_list", ratingList);
        resp.put("time_list", timeList);
        System.out.println(ratingList);
        System.out.println(timeList);
        return resp;
    }
}
