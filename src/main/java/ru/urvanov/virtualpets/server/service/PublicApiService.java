package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.api.domain.LoginArg;
import ru.urvanov.virtualpets.server.controller.api.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.controller.api.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.controller.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface PublicApiService {

    void register(RegisterArgument registerArgument)
            throws ServiceException;

    void login(LoginArg loginArg)
            throws ServiceException;
    
    void recoverPassword(RecoverPasswordArg recoverPasswordArg)
            throws ServiceException;

    ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException;
}
