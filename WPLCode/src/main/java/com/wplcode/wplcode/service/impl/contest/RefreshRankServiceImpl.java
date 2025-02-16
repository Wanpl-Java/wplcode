package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.mapper.RankMapper;
import com.wplcode.wplcode.mapper.SubmissionMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.pojo.PO.Submission;
import com.wplcode.wplcode.pojo.VO.RankVO;
import com.wplcode.wplcode.service.contest.RefreshRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RefreshRankServiceImpl implements RefreshRankService {

    private final RankMapper rankMapper;
    private final SubmissionMapper submissionMapper;
    private final ContestMapper contestMapper;

    @Override
    public JSONObject refreshRank(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        Integer contestId = Integer.parseInt(data.get("contestId"));
        List<String> quickest = new ArrayList<>();
        for (int i = 0; i < 5; i ++ ) {
            quickest.add(i, "");
        }
        int A = 999999, B = 999999, C = 999999, D = 999999, E = 999999;
        int rank = 1;
        // 获取当前比赛
        Contest contest = contestMapper.selectById(contestId);
        List<RankVO> rankVOList = new ArrayList<>();
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contest_id", contestId);
        // 确保优先处理最先提交的
        queryWrapper.orderByAsc("submit_time");
        // map处理不同用户和rankVOList下标之间的映射关系
        Map<String, Integer> map = new HashMap<>();
        List<Submission> submissionList = submissionMapper.selectList(queryWrapper);
        for (Submission submission : submissionList) {
            // 取出提交的用户，然后结合map判断是否创建了该用户的rankVO
            String owner = submission.getOwner();
            String topicId = submission.getTopicId();
            String result = submission.getResult();
            if (map.containsKey(owner)) { // 已存在，根据用户名取出对应的rankVOList下标
                Integer id = map.get(owner);
                // 根据id取出对应的rankVO
                RankVO rankVO = rankVOList.get(id);
                if ("Accept".equals(result)) {
                    long dif = submission.getSubmitTime().getTime() - contest.getStartTime().getTime();
                    dif /= 1000;
                    // 需要分类考虑
                    if ("A".equals(topicId)) {
                        if (rankVO.getTopicA() == null) { // 第一次过题
                            rankVO.setTopicA(dif + "@" + "0");
                            rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                            rankVO.setPenalty(rankVO.getPenalty() + (int) dif);
                        } else {
                            // 只有之前尚未过题才需统计
                            if ("".equals(rankVO.getTopicA().split("@")[0])) {
                                rankVO.setTopicA(dif + rankVO.getTopicA());
                                rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                                String penalty_time = rankVO.getTopicA().split("@")[1];
                                // 当前总罚时=之前总罚时+过题时间+罚时时间
                                rankVO.setPenalty(rankVO.getPenalty() + (int) dif + Integer.parseInt(penalty_time));
                            }
                        }
                    } else if ("B".equals(topicId)) {
                        if (rankVO.getTopicB() == null) {
                            rankVO.setTopicB(dif + "@" + "0");
                            rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                            rankVO.setPenalty(rankVO.getPenalty() + (int) dif);
                        } else {
                            if ("".equals(rankVO.getTopicB().split("@")[0])) {
                                rankVO.setTopicB(dif + rankVO.getTopicB());
                                rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                                String penalty_time = rankVO.getTopicB().split("@")[1];
                                rankVO.setPenalty(rankVO.getPenalty() + (int) dif + Integer.parseInt(penalty_time));
                            }
                        }
                    } else if ("C".equals(topicId)) {
                        if (rankVO.getTopicC() == null) {
                            rankVO.setTopicC(dif + "@" + "0");
                            rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                            rankVO.setPenalty(rankVO.getPenalty() + (int) dif);
                        } else {
                            if ("".equals(rankVO.getTopicC().split("@")[0])) {
                                rankVO.setTopicC(dif + rankVO.getTopicC());
                                rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                                String penalty_time = rankVO.getTopicC().split("@")[1];
                                rankVO.setPenalty(rankVO.getPenalty() + (int) dif + Integer.parseInt(penalty_time));
                            }
                        }
                    } else if ("D".equals(topicId)) {
                        if (rankVO.getTopicD() == null) {
                            rankVO.setTopicD(dif + "@" + "0");
                            rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                            rankVO.setPenalty(rankVO.getPenalty() + (int) dif);
                        } else {
                            if ("".equals(rankVO.getTopicD().split("@")[0])) {
                                rankVO.setTopicD(dif + rankVO.getTopicD());
                                rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                                String penalty_time = rankVO.getTopicD().split("@")[1];
                                rankVO.setPenalty(rankVO.getPenalty() + (int) dif + Integer.parseInt(penalty_time));
                            }
                        }
                    } else if ("E".equals(topicId)) {
                        if (rankVO.getTopicE() == null) {
                            rankVO.setTopicE(dif + "@" + "0");
                            rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                            rankVO.setPenalty(rankVO.getPenalty() + (int) dif);
                        } else {
                            if ("".equals(rankVO.getTopicE().split("@")[0])) {
                                rankVO.setTopicE(dif + rankVO.getTopicE());
                                rankVO.setPassCounts(rankVO.getPassCounts() + 1);
                                String penalty_time = rankVO.getTopicE().split("@")[1];
                                rankVO.setPenalty(rankVO.getPenalty() + (int) dif + Integer.parseInt(penalty_time));
                            }
                        }
                    }
                } else {
                    if ("A".equals(topicId)) {
                        if (rankVO.getTopicA() == null) {
                            rankVO.setTopicA("@" + "300");
                        } else {
                            if ("".equals(rankVO.getTopicA().split("@")[0])) { // 如果之前尚未过题
                                int penalty_time = Integer.parseInt(rankVO.getTopicA().split("@")[1]);
                                penalty_time += 300;
                                rankVO.setTopicA("@" + penalty_time);
                            }
                        }
                    } else if ("B".equals(topicId)) {
                        if (rankVO.getTopicB() == null) {
                            rankVO.setTopicB("@" + "300");
                        } else {
                            if ("".equals(rankVO.getTopicB().split("@")[0])) {
                                int penalty_time = Integer.parseInt(rankVO.getTopicB().split("@")[1]);
                                penalty_time += 300;
                                rankVO.setTopicB("@" + penalty_time);
                            }
                        }
                    } else if ("C".equals(topicId)) {
                        if (rankVO.getTopicC() == null) {
                            rankVO.setTopicC("@" + "300");
                        } else {
                            if ("".equals(rankVO.getTopicC().split("@")[0])) {
                                int penalty_time = Integer.parseInt(rankVO.getTopicC().split("@")[1]);
                                penalty_time += 300;
                                rankVO.setTopicC("@" + penalty_time);
                            }
                        }
                    } else if ("D".equals(topicId)) {
                        if (rankVO.getTopicD() == null) {
                            rankVO.setTopicD("@" + "300");
                        } else {
                            if ("".equals(rankVO.getTopicD().split("@")[0])) {
                                int penalty_time = Integer.parseInt(rankVO.getTopicD().split("@")[1]);
                                penalty_time += 300;
                                rankVO.setTopicD("@" + penalty_time);
                            }
                        }
                    } else if ("E".equals(topicId)) {
                        if (rankVO.getTopicE() == null) {
                            rankVO.setTopicE("@" + "300");
                        } else {
                            if ("".equals(rankVO.getTopicE().split("@")[0])) {
                                int penalty_time = Integer.parseInt(rankVO.getTopicE().split("@")[1]);
                                penalty_time += 300;
                                rankVO.setTopicE("@" + penalty_time);
                            }
                        }
                    }
                }
            } else { // 不存在，直接创建一个新的rankVO
                RankVO rankVO = new RankVO();
                rankVO.setUsername(owner);
                // 一定是首次提交且正确
                if ("Accept".equals(result)) {
                    rankVO.setPassCounts(1);
                    long dif = submission.getSubmitTime().getTime() - contest.getStartTime().getTime();
                    dif /= 1000; // ms->s
                    rankVO.setPenalty((int) dif);
                    if ("A".equals(topicId)) {
                        // "0"表示该题无有额外罚时
                        rankVO.setTopicA(dif + "@" + "0");
                    } else if ("B".equals(topicId)) {
                        rankVO.setTopicB(dif + "@" + "0");
                    } else if ("C".equals(topicId)) {
                        rankVO.setTopicC(dif + "@" + "0");
                    } else if ("D".equals(topicId)) {
                        rankVO.setTopicD(dif + "@" + "0");
                    } else if ("E".equals(topicId)) {
                        rankVO.setTopicE(dif + "@" + "0");
                    }
                } else {
                    // 没通过题也要先置为0
                    rankVO.setPassCounts(0);
                    // 因为还没有过题，所以罚时先置为0
                    rankVO.setPenalty(0);
                    if ("A".equals(topicId)) {
                        rankVO.setTopicA("@" + "300"); // 1次罚时300s
                    } else if ("B".equals(topicId)) {
                        rankVO.setTopicB("@" + "300");
                    } else if ("C".equals(topicId)) {
                        rankVO.setTopicC("@" + "300");
                    } else if ("D".equals(topicId)) {
                        rankVO.setTopicD("@" + "300");
                    } else if ("E".equals(topicId)) {
                        rankVO.setTopicE("@" + "300");
                    }
                }
                // 取出当前rankVOList的size作为新rankVO的id
                Integer len = rankVOList.size();
                rankVOList.add(rankVO);
                // map新增映射关系
                map.put(owner, len);
            }
        }

        rankVOList.sort((o1, o2) -> {
            // 先根据过题数从大到小排序
            if (!Objects.equals(o1.getPassCounts(), o2.getPassCounts())) {
                return o2.getPassCounts() - o1.getPassCounts();
            } else { // 再根据罚时从小到大排序
                return o1.getPenalty() - o2.getPenalty();
            }
        });
        int topic_len = contest.getContent().split("#").length - 1;
        for (RankVO rankVO : rankVOList) {
            rankVO.setRank(rank ++ );
            if (rankVO.getTopicA() != null) {
                int tmpA = 999999;
                if (!"".equals(rankVO.getTopicA().split("@")[0])) {
                    tmpA = Integer.parseInt(rankVO.getTopicA().split("@")[0]) + Integer.parseInt(rankVO.getTopicA().split("@")[1]);
                }
                if (tmpA < A) {
                    A = tmpA;
                    quickest.set(0, rankVO.getUsername());
                }
            } else {
                rankVO.setTopicA("null");
            }
            if (rankVO.getTopicB() != null) {
                int tmpB = 999999;
                if (!"".equals(rankVO.getTopicB().split("@")[0])) {
                    tmpB = Integer.parseInt(rankVO.getTopicB().split("@")[0]) + Integer.parseInt(rankVO.getTopicB().split("@")[1]);
                }
                if (tmpB < B) {
                    B = tmpB;
                    quickest.set(1, rankVO.getUsername());
                }
            } else {
                rankVO.setTopicB("null");
            }
            if (rankVO.getTopicC() != null) {
                int tmpC = 999999;
                if (!"".equals(rankVO.getTopicC().split("@")[0])) {
                    tmpC = Integer.parseInt(rankVO.getTopicC().split("@")[0]) + Integer.parseInt(rankVO.getTopicC().split("@")[1]);
                }
                if (tmpC < C) {
                    C = tmpC;
                    quickest.set(2, rankVO.getUsername());
                }
            } else {
                rankVO.setTopicC("null");
            }
            if (rankVO.getTopicD() != null) {
                int tmpD = 999999;
                if (!"".equals(rankVO.getTopicD().split("@")[0])) {
                    tmpD = Integer.parseInt(rankVO.getTopicD().split("@")[0]) + Integer.parseInt(rankVO.getTopicD().split("@")[1]);
                }
                if (tmpD < D) {
                    D = tmpD;
                    quickest.set(3, rankVO.getUsername());
                }
            } else {
                rankVO.setTopicD("null");
            }
            if (rankVO.getTopicE() != null) {
                int tmpE = 999999;
                if (!"".equals(rankVO.getTopicE().split("@")[0])) {
                    tmpE = Integer.parseInt(rankVO.getTopicE().split("@")[0]) + Integer.parseInt(rankVO.getTopicE().split("@")[1]);
                }
                if (tmpE < E) {
                    E = tmpE;
                    quickest.set(4, rankVO.getUsername());
                }
            } else {
                rankVO.setTopicE("null");
            }
        }
        resp.set("ranks", rankVOList);
        resp.set("topic_len", topic_len);
        resp.set("quickest", quickest);
        return resp;
    }
}
