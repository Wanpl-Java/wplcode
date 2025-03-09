package com.wplcode.wplcode.utils.XunFeiAi;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.listener.SparkBaseListener;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SparkManager {

    @Autowired
    private SparkClient sparkClient;

    public static final String PRECONDITION = "你是一名代码分析师。\n" +
                                              "可以对代码的好坏以及正确与否进行良好的分析。";

    public String sendHttpTOSpark(final String content) {
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.systemContent(PRECONDITION));
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 指定请求版本，lite版本是v4.0
                .apiVersion(SparkApiVersion.V4_0)
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String responseContent = chatResponse.getContent();
        System.out.println("Spark AI 返回的结果{}" + responseContent);
        return responseContent;
    }

}
