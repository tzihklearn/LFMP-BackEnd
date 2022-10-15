package com.MinNiCup.lfmpbackend.utils.WebSocket;

import com.MinNiCup.lfmpbackend.mapper.MessageMapper;
import com.MinNiCup.lfmpbackend.pojo.domain.Message;
import com.MinNiCup.lfmpbackend.pojo.dto.param.SendMessageParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.MessageResult;
import com.MinNiCup.lfmpbackend.utils.TimeUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class MessageUtil {
    @Resource
    private MessageMapper messageMapper;

    private static MessageUtil messageUtil;

    public static String sendMessage(Integer from, Integer to, SendMessageParam param) {
        Message message = new Message();
        message.setMessage(param.getMessage());
        message.setDate(TimeUtils.getNow());
        message.setFrom(from);
        message.setTo(to);
        messageUtil.messageMapper.insertMessage(message);
        MessageResult result = new MessageResult(message, false);
        return MessageResult2MsgJson(result);
    }

    @PostConstruct
    public void init() {
        messageUtil = this;
        messageUtil.messageMapper = this.messageMapper;
    }

    public static String MessageResult2MsgJson(MessageResult messageResult) {
        String result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
            objectMapper.writeValue(jsonGenerator, messageResult);
            jsonGenerator.close();
            result = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<MessageResult> getStoredMessage(Integer from, Integer consultId) {
        List<Message> messages = messageUtil.messageMapper.selectMessageByConsultId(consultId);
        List<MessageResult> results = new ArrayList<>();
        for (Message message : messages) {
            results.add(new MessageResult(message, Objects.equals(message.getFrom(), from)));
        }
        return results;
    }
    public static SendMessageParam msgParamJson2MsgParam(String paramJson){
        SendMessageParam result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(paramJson, SendMessageParam.class);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
        }
        return result;
    }
}
