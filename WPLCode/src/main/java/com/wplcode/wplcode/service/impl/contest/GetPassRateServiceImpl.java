package com.wplcode.wplcode.service.impl.contest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.SubmissionMapper;
import com.wplcode.wplcode.pojo.PO.Submission;
import com.wplcode.wplcode.service.contest.GetPassRateService;
import com.wplcode.wplcode.utils.PassRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPassRateServiceImpl implements GetPassRateService {

    private final SubmissionMapper submissionMapper;

    @Override
    public List<PassRate> getPassRate(String contestId) {
        List<PassRate> resp = new ArrayList<>();
        PassRate A = new PassRate(0, 0);
        PassRate B = new PassRate(0, 0);
        PassRate C = new PassRate(0, 0);
        PassRate D = new PassRate(0, 0);
        PassRate E = new PassRate(0, 0);
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contest_id", Integer.parseInt(contestId));
        List<Submission> submissions = submissionMapper.selectList(queryWrapper);
        for (Submission submission : submissions) {
            String topicId = submission.getTopicId();
            String result = submission.getResult();
            if ("A".equals(topicId)) {
                A.setSubmitTimes(A.getSubmitTimes() + 1);
                if ("Accept".equals(result)) {
                    A.setPassTimes(A.getPassTimes() + 1);
                }
            } else if ("B".equals(topicId)) {
                B.setSubmitTimes(B.getSubmitTimes() + 1);
                if ("Accept".equals(result)) {
                    B.setPassTimes(B.getPassTimes() + 1);
                }
            } else if ("C".equals(topicId)) {
                C.setSubmitTimes(C.getSubmitTimes() + 1);
                if ("Accept".equals(result)) {
                    C.setPassTimes(C.getPassTimes() + 1);
                }
            } else if ("D".equals(topicId)) {
                D.setSubmitTimes(D.getSubmitTimes() + 1);
                if ("Accept".equals(result)) {
                    D.setPassTimes(D.getPassTimes() + 1);
                }
            } else if ("E".equals(topicId)) {
                E.setSubmitTimes(E.getSubmitTimes() + 1);
                if ("Accept".equals(result)) {
                    E.setPassTimes(E.getPassTimes() + 1);
                }
            }
        }
        resp.add(A);
        resp.add(B);
        resp.add(C);
        resp.add(D);
        resp.add(E);
        return resp;
    }
}
