package com.example.chartroom.event_listeners;

import com.example.chartroom.DAL.ActiveUsersRepository;
import com.example.chartroom.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.annotation.Resource;

@Component
public class WebSocketEventListener {

    @Resource
    private SimpMessageSendingOperations smpTemplate;

    @Resource
    ActiveUsersRepository activeUsersRepository;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void onSessionConnected(SessionConnectedEvent sessionConnectedEvent) {
    }

    @EventListener
    public void onSessionDisconnected(SessionDisconnectEvent sessionDisonnectedEvent) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(sessionDisonnectedEvent.getMessage());
        activeUsersRepository.DisconnectedUser(headerAccessor.getSessionId());
        Message msg = new Message();
        msg.setOnlineCount(activeUsersRepository.getActiveUsers().size());
        smpTemplate.convertAndSend("/topic/logout", msg);
    }
}
