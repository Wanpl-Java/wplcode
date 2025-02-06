package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.AnalyseProvinceService;
import com.wplcode.wplcode.utils.ProvinceCount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnalyseProvinceController {

    private final AnalyseProvinceService analyseProvinceService;

    @GetMapping("/analyseProvince/")
    public List<ProvinceCount> analyseProvince() {
        return analyseProvinceService.getProvinceCount();
    }
}
