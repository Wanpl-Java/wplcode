package com.wplcode.wplcode.service.impl.contest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.ContestResultMapper;
import com.wplcode.wplcode.pojo.PO.ContestResult;
import com.wplcode.wplcode.service.contest.GetRegisterCountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetRegisterCountsServiceImpl implements GetRegisterCountsService {

    private final ContestResultMapper contestResultMapper;

    @Override
    public Integer getRegisterCounts(String contestId) {
        QueryWrapper<ContestResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contest_id", contestId);
        return Math.toIntExact(contestResultMapper.selectCount(queryWrapper));
    }
}
