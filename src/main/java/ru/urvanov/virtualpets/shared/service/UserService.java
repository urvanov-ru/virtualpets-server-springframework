/**
 * 
 */
package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.LoginArg;
import ru.urvanov.virtualpets.shared.domain.LoginResult;
import ru.urvanov.virtualpets.shared.domain.RefreshUsersOnlineArg;
import ru.urvanov.virtualpets.shared.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.shared.domain.UserInformation;
import ru.urvanov.virtualpets.shared.domain.UserInformationArg;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

/**
 * @author fedya
 * 
 */
public interface UserService {

    public LoginResult login(LoginArg arg) throws ServiceException, DaoException;

    public RefreshUsersOnlineResult getUsersOnline(RefreshUsersOnlineArg argument)
            throws ServiceException, DaoException;

    public UserInformation getUserInformation(UserInformationArg argument)
            throws ServiceException, DaoException;
    
    public void updateUserInformation(UserInformation arg)
            throws ServiceException, DaoException;
    
    public void closeSession() throws DaoException, ServiceException;;
}
