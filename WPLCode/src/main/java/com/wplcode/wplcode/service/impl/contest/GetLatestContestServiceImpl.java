package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.service.contest.GetLatestContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GetLatestContestServiceImpl implements GetLatestContestService {

    private final ContestMapper contestMapper;

    @Override
    public JSONObject getLatestContest() {
        JSONObject resp = new JSONObject();
        QueryWrapper<Contest> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("start_time");
        List<Contest> contests = contestMapper.selectList(queryWrapper);
        Date now = new Date();
        for (Contest contest : contests) {
            Date startTime = contest.getStartTime();
            if (now.before(startTime)) {
                Date registrationTime = contest.getRegistrationTime();
                if (now.after(registrationTime)) {
                    resp.set("msg", true);
                } else {
                    resp.set("msg", false);
                }
                long dif = startTime.getTime() - now.getTime();
                long days = TimeUnit.MILLISECONDS.toDays(dif);
                resp.set("contest", contest);
                resp.set("days", days);
                break;
            }
        }
        return resp;
    }
}
