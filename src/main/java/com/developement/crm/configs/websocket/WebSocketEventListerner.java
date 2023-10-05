package com.developement.crm.configs.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListerner {
    @EventListener
    public void handleWebScoketDisconennctListener(SessionDisconnectEvent event){
//              IMPLEMENTATION PENDENT
    }
}
