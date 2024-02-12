/**
 * 
 */
package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.GetServersArg;
import ru.urvanov.virtualpets.shared.domain.LoginResult;
import ru.urvanov.virtualpets.shared.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.shared.domain.RecoverPasswordResult;
import ru.urvanov.virtualpets.shared.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.shared.domain.RegisterArgument;
import ru.urvanov.virtualpets.shared.domain.ServerInfo;
import ru.urvanov.virtualpets.shared.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

/**
 * @author fedya
 *
 */
public interface PublicService {
    ServerInfo[] getServers(GetServersArg arg) throws ServiceException, DaoException;
    void register(RegisterArgument arg) throws ServiceException, DaoException;
    RecoverPasswordResult recoverPassword(RecoverPasswordArg argument) throws ServiceException, DaoException;
    LoginResult recoverSession(RecoverSessionArg arg) throws ServiceException, DaoException;
    ServerTechnicalInfo getServerTechnicalInfo() throws ServiceException, DaoException;
}
