/**
 * 
 */
package ru.urvanov.virtualpets.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.urvanov.virtualpets.server.dao.ChatDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Chat;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.shared.domain.ChatMessage;
import ru.urvanov.virtualpets.shared.domain.RefreshChatArg;
import ru.urvanov.virtualpets.shared.domain.RefreshChatResult;
import ru.urvanov.virtualpets.shared.domain.SendChatMessageArg;
import ru.urvanov.virtualpets.shared.domain.SendChatMessageResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.ChatService;

/**
 * @author fedya
 *
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;
    
    @Autowired
    private UserDao userDao;


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
        user.setActiveDate(new Date());
        userDao.save(user);
        
        
        Integer id = arg.getLastChatMessageId();
        List<Chat> messages;
        if (id == null) {
            messages = chatDao.findLast(20, userId);
        } else {
            messages = chatDao.findFromId(id, userId);
        }
        
        RefreshChatResult result = new RefreshChatResult();
        result.setSuccess(true);
        
        ChatMessage[] chatMessages = new ChatMessage[messages.size()];
        int n = 0;
        for(Chat c: messages) {
            chatMessages[n] = new ChatMessage();
            User addressee = c.getAddressee();
            if (addressee != null) {
                chatMessages[n].setAddresseeId(addressee.getId());
                chatMessages[n].setAddresseeName(addressee.getName());
            }
            User sender = c.getSender();
            if (sender != null) {
                chatMessages[n].setSenderId(sender.getId());
                chatMessages[n].setSenderName(sender.getName());
            }
            chatMessages[n].setMessage(c.getMessage());
            chatMessages[n].setSendTime(c.getSendTime());
            chatMessages[n].setId(c.getId());
            n++;
        }
        
        if (chatMessages.length > 0) {
            result.setLastChatMessageId(chatMessages[chatMessages.length-1].getId());
        } else {
            result.setLastChatMessageId(arg.getLastChatMessageId());
        }
        result.setChatMessages(chatMessages);
        return result;
    }

    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.shared.service.ChatService#sendMessage(ru.urvanov.virtualpets.shared.domain.SendChatMessageArg)
     */
    @Override
    public SendChatMessageResult sendMessage(SendChatMessageArg arg)
            throws DaoException, ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User)authentication.getPrincipal();
        
        Chat chat = new Chat();
        if (arg.getAddresseeId() != null) {
            chat.setAddressee(userDao.findById(arg.getAddresseeId()));
        }
        chat.setMessage(arg.getMessage());
        chat.setSender(userDao.findById(user.getId()));
        chat.setSendTime(new Date());
        chatDao.save(chat);
        
        SendChatMessageResult result = new SendChatMessageResult();
        result.setSuccess(true);
        return result;
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
