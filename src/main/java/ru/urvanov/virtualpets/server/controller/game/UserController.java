package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import ru.urvanov.virtualpets.shared.domain.LoginArg;
import ru.urvanov.virtualpets.shared.domain.LoginResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.UserService;

@RestController
@RequestMapping(value = "rest/v1/UserService")
public class UserController {

    @Autowired
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.POST, value = "login")
    public LoginResult login(@RequestAttribute LoginArg loginArg) throws ServiceException, DaoException {
        return userService.login((LoginArg) RequestContextHolder.getRequestAttributes().getAttribute("loginArg", RequestAttributes.SCOPE_REQUEST));
        
    }
}
