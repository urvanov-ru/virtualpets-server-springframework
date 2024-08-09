package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.urvanov.virtualpets.server.controller.game.domain.ChatMessage;
import ru.urvanov.virtualpets.server.controller.game.domain.RefreshChatArg;
import ru.urvanov.virtualpets.server.controller.game.domain.RefreshChatResult;
import ru.urvanov.virtualpets.server.controller.game.domain.SendChatMessageArg;
import ru.urvanov.virtualpets.server.dao.ChatDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Chat;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service
public class ChatServiceImpl implements ChatGameService {

    @Autowired
    private ChatDao chatDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private Clock clock;


    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.shared.service.ChatService#getMessages(ru.urvanov.virtualpets.shared.domain.RefreshChatArg)
     */
    @Override
    public RefreshChatResult getMessages(RefreshChatArg arg)
            throws ServiceException {
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication  authentication = (Authentication) securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();
        
        user = userDao.findById(userId).orElseThrow();
        user.setActiveDate(OffsetDateTime.now(clock));
        userDao.save(user);
        
        
        Integer id = arg.lastChatMessageId();
        List<Chat> messages;
        if (id == null) {
            messages = chatDao.findLast(20, userId);
        } else {
            messages = chatDao.findFromId(id, userId);
        }
        
        
        
        List<ChatMessage> chatMessages = StreamSupport
                .stream(messages.spliterator(), false)
                .map(c -> {
            ChatMessage chatMessage = new ChatMessage();
            User addressee = c.getAddressee();
            if (addressee != null) {
                chatMessage.setAddresseeId(addressee.getId());
                chatMessage.setAddresseeName(addressee.getName());
            }
            User sender = c.getSender();
            if (sender != null) {
                chatMessage.setSenderId(sender.getId());
                chatMessage.setSenderName(sender.getName());
            }
            chatMessage.setMessage(c.getMessage());
            chatMessage.setSendTime(c.getSendTime());
            chatMessage.setId(c.getId());
            return chatMessage;
        }).toList();
        
        Integer lastChatMessageId;
        if (!chatMessages.isEmpty()) {
            lastChatMessageId = chatMessages.get(chatMessages.size() - 1).getId();
        } else {
            lastChatMessageId = arg.lastChatMessageId();
        }
        RefreshChatResult result = new RefreshChatResult(chatMessages, lastChatMessageId);
        return result;
    }

    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.shared.service.ChatService#sendMessage(ru.urvanov.virtualpets.shared.domain.SendChatMessageArg)
     */
    @Override
    public void sendMessage(SendChatMessageArg sendChatMessageArg)
            throws ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User)authentication.getPrincipal();
        
        Chat chat = new Chat();
        if (sendChatMessageArg.addresseeId() != null) {
            chat.setAddressee(userDao.getReference(
                    sendChatMessageArg.addresseeId()));
        }
        chat.setMessage(sendChatMessageArg.message());
        chat.setSender(userDao.getReference(user.getId()));
        chat.setSendTime(OffsetDateTime.now(clock));
        chatDao.save(chat);
    }


}
