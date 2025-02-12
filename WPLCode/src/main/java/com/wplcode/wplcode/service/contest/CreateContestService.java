package com.wplcode.wplcode.service.contest;

import com.wplcode.wplcode.pojo.PO.Contest;

import java.text.ParseException;
import java.util.Map;

public interface CreateContestService {
    public void createContest(Map<String, String> data) throws ParseException;
}
