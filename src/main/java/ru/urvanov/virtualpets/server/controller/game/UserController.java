package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import ru.urvanov.virtualpets.server.api.domain.LoginArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.UserApiService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "rest/v1/UserService")
public class UserController {

    @Autowired
    private UserApiService userService;

    @Autowired
    private UserPetDetails userPetDetails;

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public LoginResult login(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestAttribute LoginArg loginArg)
            throws ServiceException, DaoException {
        LoginResult result = userService.login(
                (LoginArg) RequestContextHolder.getRequestAttributes()
                        .getAttribute("loginArg",
                                RequestAttributes.SCOPE_REQUEST));
        userPetDetails.setUserId(result.userId());
        return result;
    }
}
