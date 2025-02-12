package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.service.contest.GetContestsListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GetContestsListServiceImpl implements GetContestsListService {

    private final ContestMapper contestMapper;

    @Override
    public JSONObject getContestsList() {
        JSONObject resp = new JSONObject();
        List<Contest> cou_contests = new ArrayList<>(), past_contests = new ArrayList<>();
        List<Map<String, Integer>> cou_lens = new ArrayList<>(), past_lens = new ArrayList<>();
        List<String> cou_registration_to_start_lens = new ArrayList<>();
        QueryWrapper<Contest> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("start_time");
        List<Contest> lst = contestMapper.selectList(queryWrapper);
        for (Contest contest : lst) {
            Date now = new Date();
            Date startTime = contest.getStartTime();
            Date endTime = contest.getEndTime();
            Date registrationTime = contest.getRegistrationTime();
            if (now.after(endTime)) {
                continue;
            } else {
                cou_contests.add(contest);
                Map<String, Integer> map = new HashMap<>();
                // 毫秒数
                long len = endTime.getTime() - startTime.getTime();
                // 将毫秒差转换为天、小时、分钟和秒
                long days = TimeUnit.MILLISECONDS.toDays(len);
                long hours = TimeUnit.MILLISECONDS.toHours(len) - (days * 24);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(len) - (TimeUnit.MILLISECONDS.toHours(len) * 60);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(len) - (TimeUnit.MILLISECONDS.toMinutes(len) * 60);
                map.put("days", (int) days);
                map.put("hours", (int) hours);
                map.put("minutes", (int) minutes);
                map.put("seconds", (int) seconds);
                if (now.after(registrationTime)) {
                    cou_registration_to_start_lens.add("ok");
                } else {
                    long dif = registrationTime.getTime() - now.getTime();
                    long dif_days = TimeUnit.MILLISECONDS.toDays(dif);
                    long dif_hours = TimeUnit.MILLISECONDS.toHours(dif) - (dif_days * 24);
                    cou_registration_to_start_lens.add(dif_days + " days " + dif_hours + " hours");
                }
                cou_lens.add(map);
            }
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("end_time");
        lst = contestMapper.selectList(queryWrapper);
        for (Contest contest : lst) {
            Date now = new Date();
            Date startTime = contest.getStartTime();
            Date endTime = contest.getEndTime();
            if (now.after(endTime)) {
                past_contests.add(contest);
                Map<String, Integer> map = new HashMap<>();
                // 毫秒数
                long len = endTime.getTime() - startTime.getTime();
                // 将毫秒差转换为天、小时、分钟和秒
                long days = TimeUnit.MILLISECONDS.toDays(len);
                long hours = TimeUnit.MILLISECONDS.toHours(len) - (days * 24);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(len) - (TimeUnit.MILLISECONDS.toHours(len) * 60);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(len) - (TimeUnit.MILLISECONDS.toMinutes(len) * 60);
                map.put("days", (int) days);
                map.put("hours", (int) hours);
                map.put("minutes", (int) minutes);
                map.put("seconds", (int) seconds);
                past_lens.add(map);
            }
        }
        resp.set("cou_contests", cou_contests);
        resp.set("past_contests", past_contests);
        resp.set("cou_lens", cou_lens);
        resp.set("past_lens", past_lens);
        resp.set("cou_registration_to_start_lens", cou_registration_to_start_lens);
        return resp;
    }
}
