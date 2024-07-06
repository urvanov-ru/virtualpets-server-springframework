package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.urvanov.virtualpets.server.api.domain.GetServersArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.api.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.server.api.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.api.domain.ServerInfo;
import ru.urvanov.virtualpets.server.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.service.PublicApiService;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController    // (1)
@RequestMapping(value = "rest/v1/PublicService") // (2)
public class PublicController extends ControllerBase { // (3)

    @Autowired
    public PublicApiService publicService;

    @RequestMapping(method = RequestMethod.GET, value = "servers") // (4)
    public ServerInfo[] getServers(
            @RequestParam(name = "version") String version) // (5)
            throws ServiceException {
        GetServersArg arg = new GetServersArg(version);
        return publicService.getServers(arg); // (6)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // (7)
    @RequestMapping(method = RequestMethod.POST, value = "register")//(8)
    public void register(@RequestBody RegisterArgument arg) // (9)
            throws ServiceException {
        publicService.register(arg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST, value = "recoverPassword")
    public void recoverPassword(RecoverPasswordArg argument)
            throws ServiceException {
        publicService.recoverPassword(argument);
    }

    @RequestMapping(method = RequestMethod.POST, value = "recoverSession")
    public LoginResult recoverSession(RecoverSessionArg arg)
            throws ServiceException {
        return publicService.recoverSession(arg);
    }

    @RequestMapping(method = RequestMethod.GET, value = "server-technical-info")
    public ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException {
        return publicService.getServerTechnicalInfo();
    }

}
