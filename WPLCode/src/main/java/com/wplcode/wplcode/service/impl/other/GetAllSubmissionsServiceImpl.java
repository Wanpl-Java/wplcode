package com.wplcode.wplcode.service.impl.other;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wplcode.wplcode.mapper.SubmissionMapper;
import com.wplcode.wplcode.pojo.PO.Submission;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.service.other.GetAllSubmissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllSubmissionsServiceImpl implements GetAllSubmissionsService {

    private final SubmissionMapper submissionMapper;

    @Override
    public JSONObject getAllSubmissions(Integer page) {
        JSONObject resp = new JSONObject();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper<Submission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner", user.getUsername());
        queryWrapper.orderByDesc("submit_time");
        IPage<Submission> submissionPage = new Page<>(page, 15);
        List<Submission> submissions = submissionMapper.selectPage(submissionPage, queryWrapper).getRecords();
        resp.set("submissions", submissions);
        resp.set("submission_counts", submissionMapper.selectCount(queryWrapper));
        return resp;
    }
}
