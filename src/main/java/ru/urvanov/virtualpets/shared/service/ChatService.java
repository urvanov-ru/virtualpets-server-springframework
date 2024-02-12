/**
 * 
 */
package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.RefreshChatArg;
import ru.urvanov.virtualpets.shared.domain.RefreshChatResult;
import ru.urvanov.virtualpets.shared.domain.SendChatMessageArg;
import ru.urvanov.virtualpets.shared.domain.SendChatMessageResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

/**
 * @author fedya
 *
 */
public interface ChatService {
    public RefreshChatResult getMessages(RefreshChatArg arg)
            throws DaoException, ServiceException;

    public SendChatMessageResult sendMessage(SendChatMessageArg arg)
            throws DaoException, ServiceException;
}
