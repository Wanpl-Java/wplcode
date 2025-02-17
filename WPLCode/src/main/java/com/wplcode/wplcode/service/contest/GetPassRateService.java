package com.wplcode.wplcode.service.contest;

import com.wplcode.wplcode.utils.PassRate;

import java.util.List;

public interface GetPassRateService {
    public List<PassRate> getPassRate(String contestId);
}
