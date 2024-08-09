package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.controller.game.domain.LoginArg;
import ru.urvanov.virtualpets.server.controller.game.domain.LoginResult;
import ru.urvanov.virtualpets.server.controller.game.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.controller.game.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.controller.game.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.dao.domain.Role;
import ru.urvanov.virtualpets.server.service.PublicGameService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController // (1)
@RequestMapping(value = "api/v1/PublicService") // (2)
public class PublicController extends ControllerBase { // (3)

    @Autowired
    public PublicGameService publicService;

    @Autowired
    private UserPetDetails userPetDetails;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private SecurityContextRepository securityContextRepository;

    @ResponseStatus(HttpStatus.NO_CONTENT) // (1)
    @RequestMapping(method = RequestMethod.POST, value = "register")//(2)
    public void register(
            @RequestBody @Valid RegisterArgument registerArgument) // (3)
            throws ServiceException {
        publicService.register(registerArgument);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST,
            value = "recoverPassword")
    public void recoverPassword(
            @RequestBody @Valid RecoverPasswordArg recoverPasswordArg)
            throws ServiceException {
        publicService.recoverPassword(recoverPasswordArg);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "server-technical-info")
    public ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException {
        return publicService.getServerTechnicalInfo();
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public LoginResult login(
            // @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid LoginArg loginArg,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
            throws ServiceException {
        
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginArg.login(), loginArg.password());
        Authentication authenticationResponse;
        authenticationResponse = 
                this.authenticationManager.authenticate(authenticationRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationResponse);
        securityContextRepository.saveContext(securityContext, httpServletRequest, httpServletResponse);
        httpServletRequest.setAttribute("remember-me", true);
        LoginResult result = publicService.login(loginArg);
        userPetDetails.setUserId(result.userId());
        return result;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "checkSession")
    public LoginResult checkSession(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
            throws ServiceException {
        if (userDetailsImpl != null && userDetailsImpl.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_" + Role.USER.name()))) {
            userPetDetails.setUserId(userDetailsImpl.getUserId());
            return new LoginResult(true, null, userDetailsImpl.getUserId(), userDetailsImpl.getUsername(), userDetailsImpl.getName());
        }
        return new LoginResult(false, null, null, null, null);
    }
}
