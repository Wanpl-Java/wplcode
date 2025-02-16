package com.wplcode.wplcode.service.impl.contest;

import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.contest.CreateContestService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateContestServiceImpl implements CreateContestService {

    private final ContestMapper contestMapper;

    @Override
    public void createContest(Map<String, String> data) throws ParseException {
        String name = data.get("name");
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        String writer = user.getUsername();
        String startTime = data.get("start_time");
        String endTime = data.get("end_time");
        String registrationTime = data.get("registration_time");
        String content = data.get("content");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse(startTime);
        Date endDate = sdf.parse(endTime);
        Date registrationDate = sdf.parse(registrationTime);
        System.out.println(content);
        Contest contest = new Contest(
                null,
                name,
                writer,
                startDate,
                endDate,
                registrationDate,
                content,
                0
        );
        contestMapper.insert(contest);
    }
}
