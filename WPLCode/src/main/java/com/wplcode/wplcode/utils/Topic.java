package com.wplcode.wplcode.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private String topicId;
    private String title;
    private String content;
    private String inputFormat;
    private String outputFormat;
    private String dataRange;
    private String inputExample;
    private String outputExample;
}
