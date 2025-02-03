package com.wplcode.wplcode.pojo.DTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String confirmedPassword;
    @Email(message = "Please enter the correct email format!")
    private String email;
    private String code;
}
