package com.developement.crm.controllers;

import com.developement.crm.dtos.ChatMessageDto;
import com.developement.crm.dtos.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/connect")
    @SendTo("/chat")
    public ChatMessageDto send(ChatMessageDto msg) throws Exception {
        return new ChatMessageDto(msg.message(), msg.userName(), msg.token());
    }
}
