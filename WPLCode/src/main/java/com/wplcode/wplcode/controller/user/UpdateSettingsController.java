package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.UpdateSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UpdateSettingsController {

    private final UpdateSettingsService updateSettingsService;

    @PutMapping("/updateSettings/")
    public Map<String, String> updateSettings(@RequestParam Map<String, String> data) {
        return updateSettingsService.updateSettings(data);
    }
}
