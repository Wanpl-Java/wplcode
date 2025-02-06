package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.AnalyseCityService;
import com.wplcode.wplcode.utils.CityCount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnalyseCityController {

    private final AnalyseCityService analyseCityService;

    @GetMapping("/analyseCity/")
    public List<CityCount> analyseCity() {
        return analyseCityService.analyseCity();
    }
}
