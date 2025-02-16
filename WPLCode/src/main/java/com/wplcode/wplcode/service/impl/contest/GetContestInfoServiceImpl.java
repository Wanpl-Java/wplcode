package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.service.contest.GetContestInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GetContestInfoServiceImpl implements GetContestInfoService {

    private final ContestMapper contestMapper;

    @Override
    public JSONObject getContestInfo(String contestId) {
        JSONObject resp = new JSONObject();
        Contest contest = contestMapper.selectById(Integer.parseInt(contestId));
        long dif = contest.getEndTime().getTime() - contest.getStartTime().getTime();
        dif /= 1000 * 60;
        double db_hours = dif / 60.0;
        dif = contest.getStartTime().getTime() - new Date().getTime();
        // 将毫秒差转换为天、小时、分钟和秒
        long days = TimeUnit.MILLISECONDS.toDays(dif);
        long hours = TimeUnit.MILLISECONDS.toHours(dif) - (days * 24);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(dif) - (TimeUnit.MILLISECONDS.toHours(dif) * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(dif) - (TimeUnit.MILLISECONDS.toMinutes(dif) * 60);
        resp.set("contest", contest);
        resp.set("db_hours", db_hours);
        resp.set("days", days);
        resp.set("hours", hours);
        resp.set("minutes", minutes);
        resp.set("seconds", seconds);
        return resp;
    }
}
