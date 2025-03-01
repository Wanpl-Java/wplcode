package com.wplcode.wplcode.utils.gym;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteMessage {

    private Integer exitCode;

    private String message;

    private String errorMessage;

    private Long executeTime;

    private Long executeMemory;
}
