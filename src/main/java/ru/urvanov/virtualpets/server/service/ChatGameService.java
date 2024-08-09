package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.game.domain.RefreshChatArg;
import ru.urvanov.virtualpets.server.controller.game.domain.RefreshChatResult;
import ru.urvanov.virtualpets.server.controller.game.domain.SendChatMessageArg;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface ChatGameService {
    RefreshChatResult getMessages(RefreshChatArg arg)
            throws ServiceException;

    void sendMessage(SendChatMessageArg arg)
            throws ServiceException;
}
