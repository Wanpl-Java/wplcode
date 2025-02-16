package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.SubmissionMapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.Submission;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.pojo.VO.SubmissionVO;
import com.wplcode.wplcode.service.contest.GetSubmissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSubmissionsServiceImpl implements GetSubmissionsService {

    private final SubmissionMapper submissionMapper;
    private final UserMapper userMapper;

    @Override
    public JSONObject getSubmissions(String contestId, String topicId, String username) {
        JSONObject resp = new JSONObject();
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.eq("owner", username);
        }
        queryWrapper.eq("contest_id", Integer.parseInt(contestId));
        if (!"".equals(topicId)) {
            queryWrapper.eq("topic_id", topicId);
        }
        queryWrapper.orderByDesc("submit_time");
        List<Submission> submissions = submissionMapper.selectList(queryWrapper);
        List<SubmissionVO> submissionVOList = new ArrayList<>();
        for (Submission submission : submissions) {
            User user = userMapper.findByUsername(submission.getOwner());
            SubmissionVO submissionVO = new SubmissionVO(
                    submission.getId(),
                    submission.getOwner(),
                    user.getRating(),
                    submission.getTopicId(),
                    submission.getResult(),
                    submission.getSubmitTime()
            );
            submissionVOList.add(submissionVO);
        }
        resp.set("submissions", submissionVOList);
        return resp;
    }
}
