package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.ContestResultMapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.ContestResult;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.pojo.VO.ContestResultVO;
import com.wplcode.wplcode.service.contest.GetContestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetContestResultServiceImpl implements GetContestResultService {

    private final ContestResultMapper contestResultMapper;
    private final UserMapper userMapper;

    @Override
    public JSONObject getContestResult(Integer contestId) {
        JSONObject jsonObject = new JSONObject();
        List<String> quickest = new ArrayList<>();
        for (int i = 0; i < 5; i ++ ) {
            quickest.add(i, "");
        }
        int A = 999999, B = 999999, C = 999999, D = 999999, E = 999999;
        List<ContestResultVO> resp = new ArrayList<>();
        QueryWrapper<ContestResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contest_id", contestId);
        // 罚时越少排名越靠前
        queryWrapper.orderByAsc("penalty");
        List<ContestResult> lst = contestResultMapper.selectList(queryWrapper);
        int rank = 1;
        for (ContestResult contestResult : lst) {
            int id = 0;
            User user = userMapper.selectById(contestResult.getUserId());
            List<Integer> results = new ArrayList<>();
            List<Integer> failedResults = new ArrayList<>();
            String result = contestResult.getResult();
            int passCounts = 0;
            if (result != null) {
                String[] split = result.split("@");
                for (String s : split) {
                    if (!"".equals(s)) {
                        if (!s.contains("#")) {
                            int num_time = Integer.parseInt(s);
                            if (num_time > 0) {
                                if (id == 0) {
                                    if (num_time < A) {
                                        A = num_time;
                                        quickest.set(0, user.getUsername());
                                    }
                                } else if (id == 1) {
                                    if (num_time < B) {
                                        B = num_time;
                                        quickest.set(1, user.getUsername());
                                    }
                                } else if (id == 2) {
                                    if (num_time < C) {
                                        C = num_time;
                                        quickest.set(2, user.getUsername());
                                    }
                                } else if (id == 3) {
                                    if (num_time < D) {
                                        D = num_time;
                                        quickest.set(3, user.getUsername());
                                    }
                                } else if (id == 4) {
                                    if (num_time < E) {
                                        E = num_time;
                                        quickest.set(4, user.getUsername());
                                    }
                                }
                                passCounts ++;
                            }
                            num_time /= 60;
                            results.add(num_time);
                            failedResults.add(0);
                        } else {
                            int num_time = Integer.parseInt(s.split("#")[0]);
                            if (num_time > 0) {
                                if (id == 0) {
                                    if (num_time < A) {
                                        A = num_time;
                                        quickest.set(0, user.getUsername());
                                    }
                                } else if (id == 1) {
                                    if (num_time < B) {
                                        B = num_time;
                                        quickest.set(1, user.getUsername());
                                    }
                                } else if (id == 2) {
                                    if (num_time < C) {
                                        C = num_time;
                                        quickest.set(2, user.getUsername());
                                    }
                                } else if (id == 3) {
                                    if (num_time < D) {
                                        D = num_time;
                                        quickest.set(3, user.getUsername());
                                    }
                                } else if (id == 4) {
                                    if (num_time < E) {
                                        E = num_time;
                                        quickest.set(4, user.getUsername());
                                    }
                                }
                                passCounts ++;
                                num_time /= 60;
                                results.add(num_time);
                            } else {
                                results.add(-1);
                            }
                            failedResults.add(Integer.parseInt(s.split("#")[1]) / 300);
                        }
                        id ++;
                    }
                }
            }
            ContestResultVO contestResultVO = new ContestResultVO(
                    rank ++,
                    user.getUsername(),
                    contestResult.getPenalty() / 60,
                    results,
                    failedResults,
                    passCounts
            );
            resp.add(contestResultVO);
        }
        jsonObject.set("contestResult", resp);
        jsonObject.set("quickest", quickest);
        return jsonObject;
    }
}
