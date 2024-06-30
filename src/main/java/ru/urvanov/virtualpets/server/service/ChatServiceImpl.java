package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.urvanov.virtualpets.server.api.domain.ChatMessage;
import ru.urvanov.virtualpets.server.api.domain.RefreshChatArg;
import ru.urvanov.virtualpets.server.api.domain.RefreshChatResult;
import ru.urvanov.virtualpets.server.api.domain.SendChatMessageArg;
import ru.urvanov.virtualpets.server.dao.ChatDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Chat;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service
public class ChatServiceImpl implements ChatApiService {

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
            throws DaoException, ServiceException {
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication  authentication = (Authentication) securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();
        
        user = userDao.findById(userId);
        user.setActiveDate(OffsetDateTime.now(clock));
        userDao.save(user);
        
        
        Integer id = arg.lastChatMessageId();
        List<Chat> messages;
        if (id == null) {
            messages = chatDao.findLast(20, userId);
        } else {
            messages = chatDao.findFromId(id, userId);
        }
        
        
        
        List<ChatMessage> chatMessages = new ArrayList<>(messages.size());
        for(Chat c: messages) {
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
            chatMessages.add(chatMessage);
            }
        
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
    public void sendMessage(SendChatMessageArg arg)
            throws DaoException, ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User)authentication.getPrincipal();
        
        Chat chat = new Chat();
        if (arg.addresseeId() != null) {
            chat.setAddressee(userDao.getReference(arg.addresseeId()));
        }
        chat.setMessage(arg.message());
        chat.setSender(userDao.getReference(user.getId()));
        chat.setSendTime(OffsetDateTime.now(clock));
        chatDao.save(chat);
    }

    public ChatDao getChatDao() {
        return chatDao;
    }

    public void setChatDao(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


}
