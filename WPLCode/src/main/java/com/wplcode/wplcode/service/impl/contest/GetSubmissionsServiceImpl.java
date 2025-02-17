package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public JSONObject getSubmissions(String contestId, String topicId, String username, Integer page) {
        JSONObject resp = new JSONObject();
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.eq("owner", username);
        }
        IPage<Submission> submissionPage = new Page<>(page, 15);
        IPage<Submission> mySubmissionPage = new Page<>(page, 15);
        queryWrapper.eq("contest_id", Integer.parseInt(contestId));
        if (!"".equals(topicId)) {
            queryWrapper.eq("topic_id", topicId);
        }
        queryWrapper.orderByDesc("submit_time");
        List<SubmissionVO> submissionVOList = new ArrayList<>();
        List<Submission> submissions;
        // MySubmissions
        if (!"".equals(username)) {
            submissions = submissionMapper.selectPage(mySubmissionPage, queryWrapper).getRecords();
            resp.set("my_submission_counts", submissionMapper.selectCount(queryWrapper));
        } else { // Submissions
            submissions = submissionMapper.selectPage(submissionPage, queryWrapper).getRecords();
            resp.set("submission_counts", submissionMapper.selectCount(queryWrapper));
        }
        for (Submission submission : submissions) {
            User user = userMapper.findByUsername(submission.getOwner());
            SubmissionVO submissionVO = new SubmissionVO(
                    submission.getId(),
                    submission.getOwner(),
                    user.getRating(),
                    submission.getTopicId(),
                    submission.getResult(),
                    submission.getSubmitTime(),
                    submission.getRunTime()
            );
            submissionVOList.add(submissionVO);
        }
        resp.set("submissions", submissionVOList);
        return resp;
    }
}
