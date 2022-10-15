package com.MinNiCup.lfmpbackend.WSClasses;

import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.domain.User;
import com.MinNiCup.lfmpbackend.pojo.dto.result.MessageResult;
import com.MinNiCup.lfmpbackend.utils.WebSocket.AttributesKeys;
import com.MinNiCup.lfmpbackend.utils.WebSocket.ConsultUtil;
import com.MinNiCup.lfmpbackend.utils.WebSocket.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Component
@Slf4j
public class HttpAuthHandler extends TextWebSocketHandler {
    /**
     * socket 建立成功事件
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        Object consultObj = attributes.get(AttributesKeys.CONSULT.getName());
        Object userObj = attributes.get(AttributesKeys.USER.getName());
        Consult consult;
        User user;
        if (consultObj instanceof Consult)
            consult = (Consult) consultObj;
        else {
            log.warn("Consult强转失败");
            afterConnectionClosed(session, CloseStatus.SERVER_ERROR);
            return;
        }
        if (userObj instanceof User)
            user = (User) userObj;
        else {
            log.warn("User强转失败");
            afterConnectionClosed(session, CloseStatus.SERVER_ERROR);
            return;
        }
        WsSessionManager.add(ConsultUtil.getSessionCode(consult, user, true), session);
        for (MessageResult messageResult : MessageUtil.getStoredMessage(user.getId(), consult.getId())) {
            sendMessage(session, MessageUtil.MessageResult2MsgJson(messageResult));
        }
        if (session.getAttributes().get("AC") == "YES")
            afterConnectionClosed(session, CloseStatus.NORMAL);
    }

    /**
     * 接收消息事件
     * @param session
     * @param message
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, @NotNull TextMessage message) {
        Map<String, Object> attributes = session.getAttributes();
        Object consultObj = attributes.get(AttributesKeys.CONSULT.getName());
        Object userObj = attributes.get(AttributesKeys.USER.getName());
        Consult consult;
        User user;
        if (consultObj instanceof Consult)
            consult = (Consult) consultObj;
        else {
            log.warn("Consult强转失败");
            afterConnectionClosed(session, CloseStatus.SERVER_ERROR);
            return;
        }
        if (userObj instanceof User)
            user = (User) userObj;
        else {
            log.warn("User强转失败");
            afterConnectionClosed(session, CloseStatus.SERVER_ERROR);
            return;
        }
        String sendMessage = MessageUtil.sendMessage(
                user.getId(),
                (user.getIdIdent() == 2 ? consult.getConsumerId() : consult.getLawyerId()),
                MessageUtil.msgParamJson2MsgParam(message.getPayload()));
        String sessionCode = ConsultUtil.getSessionCode(consult, user, false);
        WebSocketSession webSocketSession = WsSessionManager.get(sessionCode);
        if(webSocketSession != null)
            sendMessage(webSocketSession, sendMessage);
    }

    /**
     * socket 断开连接时
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        Map<String, Object> attributes = session.getAttributes();
        Object consultObj = attributes.get(AttributesKeys.CONSULT.getName());
        Object userObj = attributes.get(AttributesKeys.USER.getName());
        Consult consult;
        User user;
        if (consultObj instanceof Consult)
            consult = (Consult) consultObj;
        else {
            log.warn("Consult强转失败");
            afterConnectionClosed(session, CloseStatus.SERVER_ERROR);
            return;
        }
        if (userObj instanceof User)
            user = (User) userObj;
        else {
            log.warn("User强转失败");
            afterConnectionClosed(session, CloseStatus.SERVER_ERROR);
            return;
        }
        WsSessionManager.removeAndClose(ConsultUtil.getSessionCode(consult, user, true));
    }

    public void sendMessage(WebSocketSession session, String msg) {
        try {
            session.sendMessage(new TextMessage(msg));
        } catch (IOException e) {
            log.warn(e.getMessage());
            log.warn(Arrays.toString(e.getStackTrace()));
        }
    }
}
