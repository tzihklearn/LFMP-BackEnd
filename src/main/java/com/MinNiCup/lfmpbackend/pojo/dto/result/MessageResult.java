package com.MinNiCup.lfmpbackend.pojo.dto.result;

import com.MinNiCup.lfmpbackend.pojo.domain.Message;
import lombok.Data;

@Data
public class MessageResult {
    private boolean fromMe;
    private String message;
    private Long timestamp;

    public MessageResult(Message message, Boolean fromMe) {
        this.fromMe = fromMe;
        this.message = message.getMessage();
        this.timestamp = message.getDate();
    }
}
