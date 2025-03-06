package com.wplcode.wplcode.service.impl.gym;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wplcode.wplcode.mapper.GymSubmissionMapper;
import com.wplcode.wplcode.pojo.PO.GymSubmission;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.gym.GetGymSubmissionsService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetGymSubmissionsServiceImpl implements GetGymSubmissionsService {

    private final GymSubmissionMapper gymSubmissionMapper;

    @Override
    public JSONObject getGymSubmissions(Integer page) {
        JSONObject resp = new JSONObject();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper<GymSubmission> gymSubmissionQueryWrapper = new QueryWrapper<>();
        gymSubmissionQueryWrapper.eq("user_id", user.getId());
        gymSubmissionQueryWrapper.orderByDesc("submit_time");
        IPage<GymSubmission> gymSubmissionIPage = new Page<>(page, 15);
        List<GymSubmission> gymSubmissions = gymSubmissionMapper.selectPage(gymSubmissionIPage, gymSubmissionQueryWrapper).getRecords();
        resp.set("gymSubmissions", gymSubmissions);
        resp.set("count", gymSubmissionMapper.selectCount(gymSubmissionQueryWrapper));
        return resp;
    }
}
