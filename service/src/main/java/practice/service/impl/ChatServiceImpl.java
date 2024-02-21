package practice.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.dto.ChatMessageDto;
import practice.entity.Chat;
import practice.entity.ChatHistory;
import practice.entity.UserEntity;
import practice.repository.ChatHistoryRepository;
import practice.repository.ChatRepository;
import practice.repository.GroupRepository;
import practice.service.ChatService;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ChatHistoryRepository chatHistoryRepository;

    @Autowired
    ChatRepository chatRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveNewMessage(Integer groupId, ChatMessageDto dto) {
        Chat chat = groupRepository.findById(groupId).get().getChat();

        ChatHistory newChatMessage = ChatHistory
                .builder()
                .chat(chat)
                .content(dto.getMessage())
                .user(entityManager.getReference(UserEntity.class, dto.getUserId()))
                .build();

        chatHistoryRepository.save(newChatMessage);
    }

    @Override
    public List<ChatMessageDto> getChat(Integer groupId, int userId) {
        Chat chat = chatRepository.findByIdWithChatHistory(groupId);
        return chat.getChatMessages().stream().map(m -> ChatMessageDto
                        .builder()
                        .userId(m.getUserId())
                        .message(m.getContent())
                        .userName(m.getUser().getName())
                        .build())
                .toList();
    }
}
