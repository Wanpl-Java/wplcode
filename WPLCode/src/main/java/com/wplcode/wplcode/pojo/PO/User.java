package com.wplcode.wplcode.pojo.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    @Email(message = "Please enter the correct email format!")
    private String email;
    private String photo;
    private Integer rating;
    private Integer participation;
    private String province;
    private String city;
    private Integer friends;
    private Integer visitors;
    private String ratingList;
    private String timeList;
    private String friendList;
}
