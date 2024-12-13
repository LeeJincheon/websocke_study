package com.example.chat_service.config;

import com.example.chat_service.handler.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@EnableWebSocket
@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {

    final WebSocketChatHandler webSocketChatHandler;

//    public WebSocketConfiguration(WebSocketChatHandler webSocketChatHandler) {
//        this.webSocketChatHandler = webSocketChatHandler;
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketChatHandler, "/ws/chats");
    }
}
