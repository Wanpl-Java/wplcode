package com.wplcode.wplcode.service.impl.contest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.mapper.ContestRankMapper;
import com.wplcode.wplcode.mapper.ContestResultMapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.pojo.PO.ContestRank;
import com.wplcode.wplcode.pojo.PO.ContestResult;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.contest.UpdateContestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateContestResultServiceImpl implements UpdateContestResultService {

    private final ContestResultMapper contestResultMapper;
    private final ContestMapper contestMapper;
    private final ContestRankMapper contestRankMapper;
    private final UserMapper userMapper;

    @Override
    public void updateContestResult() {
        QueryWrapper<Contest> contestQueryWrapper = new QueryWrapper<>();
        contestQueryWrapper.orderByAsc("end_time");
        List<Contest> contests = contestMapper.selectList(contestQueryWrapper);
        Date now = new Date();
        for (Contest contest : contests) {
            Integer contestId = contest.getId();
            if (contestId == 1) { // TODO 特判处理
                continue;
            }
            Date endTime = contest.getEndTime();
            if (endTime.after(now)) {
                break;
            }
            QueryWrapper<ContestRank> rankQueryWrapper = new QueryWrapper<>();
            rankQueryWrapper.eq("contest_id", contestId);
            List<ContestRank> contestRanks = contestRankMapper.selectList(rankQueryWrapper);
            // 当前比赛总题数
            int topic_counts = contest.getContent().split("#").length - 1;
            for (ContestRank contestRank : contestRanks) {
                QueryWrapper<ContestResult> resultQueryWrapper = new QueryWrapper<>();
                resultQueryWrapper.eq("contest_id", contestId);
                resultQueryWrapper.eq("user_id", contestRank.getUserId());
                ContestResult origin_contestResult = contestResultMapper.selectOne(resultQueryWrapper);
                String result = "@"; // TODO
                String topicA = contestRank.getTopicA();
                String topicB = contestRank.getTopicB();
                String topicC = contestRank.getTopicC();
                String topicD = contestRank.getTopicD();
                String topicE = contestRank.getTopicE();
                if (topic_counts >= 1) {
                    // null表示未提交
                    if ("null".equals(topicA)) {
                        result += "@";
                    } else {
                        String[] split = topicA.split("@");
                        if ("".equals(split[0])) { // 此题未AC
                            result += "0#" + Integer.parseInt(split[1]) + "@";
                        } else if ("0".equals(split[1])) { // 此题通过且未有额外罚时
                            result += Integer.parseInt(split[0]) * 60 + "@";
                        } else { // 此题通过且有额外罚时
                            result += Integer.parseInt(split[0]) * 60 + "#" + Integer.parseInt(split[1]) + "@";
                        }
                    }
                }
                if (topic_counts >= 2) {
                    if ("null".equals(topicB)) {
                        result += "@";
                    } else {
                        String[] split = topicB.split("@");
                        if ("".equals(split[0])) {
                            result += "0#" + Integer.parseInt(split[1]) + "@";
                        } else if ("0".equals(split[1])) {
                            result += Integer.parseInt(split[0]) * 60 + "@";
                        } else {
                            result += Integer.parseInt(split[0]) * 60 + "#" + Integer.parseInt(split[1]) + "@";
                        }
                    }
                }
                if (topic_counts >= 3) {
                    if ("null".equals(topicC)) {
                        result += "@";
                    } else {
                        String[] split = topicC.split("@");
                        if ("".equals(split[0])) {
                            result += "0#" + Integer.parseInt(split[1]) + "@";
                        } else if ("0".equals(split[1])) {
                            result += Integer.parseInt(split[0]) * 60 + "@";
                        } else {
                            result += Integer.parseInt(split[0]) * 60 + "#" + Integer.parseInt(split[1]) + "@";
                        }
                    }
                }
                if (topic_counts >= 4) {
                    if ("null".equals(topicD)) {
                        result += "@";
                    } else {
                        String[] split = topicD.split("@");
                        if ("".equals(split[0])) {
                            result += "0#" + Integer.parseInt(split[1]) + "@";
                        } else if ("0".equals(split[1])) {
                            result += Integer.parseInt(split[0]) * 60 + "@";
                        } else {
                            result += Integer.parseInt(split[0]) * 60 + "#" + Integer.parseInt(split[1]) + "@";
                        }
                    }
                }
                if (topic_counts >= 5) {
                    if ("null".equals(topicE)) {
                        result += "@";
                    } else {
                        String[] split = topicE.split("@");
                        if ("".equals(split[0])) {
                            result += "0#" + Integer.parseInt(split[1]) + "@";
                        } else if ("0".equals(split[1])) {
                            result += Integer.parseInt(split[0]) * 60 + "@";
                        } else {
                            result += Integer.parseInt(split[0]) * 60 + "#" + Integer.parseInt(split[1]) + "@";
                        }
                    }
                }
                ContestResult contestResult = new ContestResult(
                        origin_contestResult.getId(),
                        contestId,
                        contestRank.getUserId(),
                        result,
                        contestRank.getPenalty() * 60,
                        5 // TODO 暂定参与比赛rating都+5,后续会改业务
                );
                contestResultMapper.updateById(contestResult);
                // 修改user rating变化表
                User user = userMapper.selectById(contestRank.getUserId());
                // 判断是否需要更新
                String nowTimeList = user.getTimeList();
                nowTimeList = nowTimeList.split(",")[nowTimeList.split(",").length - 1];
                String year = String.valueOf(endTime.getYear() + 1900);
                String month = String.valueOf(endTime.getMonth() + 1);
                String day = String.valueOf(endTime.getDate());
                if (endTime.getMonth() + 1 < 10) {
                    month = "0" + month;
                }
                if (endTime.getDate() < 10) {
                    day = "0" + day;
                }
                User newUser = new User(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getPhoto(),
                        user.getRating() + 5, // TODO 同上
                        user.getParticipation(),
                        user.getProvince(),
                        user.getCity(),
                        user.getFriends(),
                        user.getVisitors(),
                        user.getRatingList() + (user.getRating() + 5) + ",", // TODO 同上
                        user.getTimeList() + year + "-" + month + "-" + day + ",",
                        user.getFriendList()
                );
                if (!nowTimeList.equals(year + "-" + month + "-" + day)) {
                    System.out.println("\"info\" = " + "need update");
                    userMapper.updateById(newUser);
                } else {
                    System.out.println("\"info\" = " + "not need update");
                }
                // System.out.println("newUser = " + newUser);
            }
        }
    }
}
