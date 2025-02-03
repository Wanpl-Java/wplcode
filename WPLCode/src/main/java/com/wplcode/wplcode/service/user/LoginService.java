package com.wplcode.wplcode.service.user;

import java.util.Map;

public interface LoginService {
    public Map<String, String> login(String username_or_email, String password);
}
