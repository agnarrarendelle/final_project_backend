package practice.service;

import practice.dto.ChatMessageDto;

import java.util.List;

public interface ChatService {
    public void saveNewMessage(Integer groupId, ChatMessageDto dto);

    List<ChatMessageDto> getChat(Integer groupId, int userId);
}
