package com.wplcode.wplcode.controller.user;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.user.EchartsProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EchartsProvinceController {

    private final EchartsProvinceService provinceService;

    @GetMapping("/echartsProvince/")
    public JSONObject echartsProvince() {
        return provinceService.echartsProvince();
    }
}
