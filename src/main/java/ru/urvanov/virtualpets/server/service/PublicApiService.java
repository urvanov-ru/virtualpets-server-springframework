package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.game.domain.LoginArg;
import ru.urvanov.virtualpets.server.controller.game.domain.LoginResult;
import ru.urvanov.virtualpets.server.controller.game.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.controller.game.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.controller.game.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PublicApiService {

    void register(RegisterArgument registerArgument)
            throws ServiceException;

    LoginResult login(LoginArg loginArg)
            throws ServiceException;
    
    void recoverPassword(RecoverPasswordArg recoverPasswordArg)
            throws ServiceException;

    ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException;
}
