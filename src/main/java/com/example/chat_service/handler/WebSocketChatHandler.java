package com.example.chat_service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    // 웹소켓 클라이언트가 서버와 연결된 이후 로직 작성
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} connected", session.getId());
        this.webSocketSessionMap.put(session.getId(), session);
    }

    //클라이언트에서 메시지가 왔을 때 처리하는 로직
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("{} sent {}", session.getId(), message.getPayload());
        this.webSocketSessionMap.values().forEach(
                webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    // 클라이언트가 연결을 끊었을 때 처리하는 로직
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} disconnected", session.getId());
        this.webSocketSessionMap.remove(session.getId());
    }
}
