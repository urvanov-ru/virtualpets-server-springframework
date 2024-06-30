package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.server.api.domain.GetServersArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.api.domain.RecoverPasswordResult;
import ru.urvanov.virtualpets.server.api.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.server.api.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.api.domain.ServerInfo;
import ru.urvanov.virtualpets.server.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.PublicApiService;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "rest/v1/PublicService")
public class PublicController {

    @Autowired
    public PublicApiService publicService;
    

    @RequestMapping(method = RequestMethod.GET, value="servers")
    public ServerInfo[] getServers(@RequestParam(name="version") String version) throws ServiceException,
            DaoException {
        GetServersArg arg = new GetServersArg();
        arg.setVersion(version);
        return publicService.getServers(arg);
    }

    @RequestMapping(method=RequestMethod.POST, value="register")
    public void register(@RequestBody RegisterArgument arg) throws ServiceException, DaoException {
        publicService.register(arg);
    }

    @RequestMapping(method = RequestMethod.POST, value="recoverPassword")
    public RecoverPasswordResult recoverPassword(RecoverPasswordArg argument)
            throws ServiceException, DaoException {
        return publicService.recoverPassword(argument);
    }

    @RequestMapping(method = RequestMethod.POST, value = "recoverSession")
    public LoginResult recoverSession(RecoverSessionArg arg)
            throws ServiceException, DaoException {
        return publicService.recoverSession(arg);
    }

    @RequestMapping(method = RequestMethod.GET, value="server-technical-info")
    public ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException, DaoException {
        return publicService.getServerTechnicalInfo();
    }

}
