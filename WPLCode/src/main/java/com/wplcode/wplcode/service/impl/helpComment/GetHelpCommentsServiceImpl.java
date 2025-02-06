package com.wplcode.wplcode.service.impl.helpComment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wplcode.wplcode.mapper.HelpCommentMapper;
import com.wplcode.wplcode.pojo.PO.HelpComment;
import com.wplcode.wplcode.service.helpComment.GetHelpCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetHelpCommentsServiceImpl implements GetHelpCommentsService {

    private final HelpCommentMapper helpCommentMapper;

    @Override
    public Map<String, Object> getHelpComments(Integer page) {
        Map<String, Object> resp = new HashMap<>();
        QueryWrapper<HelpComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        IPage<HelpComment> helpCommentPage = new Page<>(page, 10);
        List<HelpComment> comments = helpCommentMapper.selectPage(helpCommentPage, queryWrapper).getRecords();
        resp.put("comments", comments);
        resp.put("comment_counts", helpCommentMapper.selectCount(queryWrapper));
        return resp;
    }
}
