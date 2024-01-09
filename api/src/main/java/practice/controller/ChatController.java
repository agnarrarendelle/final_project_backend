package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import practice.dto.ChatMessageDto;
import practice.service.ChatService;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;
    @MessageMapping("/group/{groupId}")
    @SendTo("/topic/group-messages/{groupId}")
    public ChatMessageDto sendMessage(ChatMessageDto message, @DestinationVariable("groupId") int groupId){
        chatService.saveNewMessage(groupId, message);
        return message;
    }
}
