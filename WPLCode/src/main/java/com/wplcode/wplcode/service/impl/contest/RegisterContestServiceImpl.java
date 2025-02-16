package com.wplcode.wplcode.service.impl.contest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.ContestResultMapper;
import com.wplcode.wplcode.pojo.PO.ContestResult;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.contest.RegisterContestService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegisterContestServiceImpl implements RegisterContestService {

    private final ContestResultMapper contestResultMapper;

    @Override
    public Map<String, String> registerContest(String contestId, String isOk) {
        Map<String, String> resp = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper<ContestResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contest_id", Integer.parseInt(contestId));
        queryWrapper.eq("user_id", user.getId());
        boolean haveRegistered = false;
        if (contestResultMapper.selectCount(queryWrapper) > 0) {
            resp.put("error_message", "have registered!");
            haveRegistered = true;
        } else {
            resp.put("error_message", "have not registered!");
        }
        if ("false".equals(isOk)) {
            return resp;
        }
        if (!haveRegistered) {
            ContestResult contestResult = new ContestResult(
                    null,
                    Integer.parseInt(contestId),
                    user.getId(),
                    "@",
                    0,
                    0
            );
            contestResultMapper.insert(contestResult);
        }
        return resp;
    }
}
