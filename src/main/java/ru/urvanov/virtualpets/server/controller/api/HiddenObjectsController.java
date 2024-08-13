package ru.urvanov.virtualpets.server.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.controller.api.domain.CollectObjectArg;
import ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame;
import ru.urvanov.virtualpets.server.controller.api.domain.JoinHiddenObjectsGameArg;
import ru.urvanov.virtualpets.server.controller.api.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.HiddenObjectsApiService;
import ru.urvanov.virtualpets.server.service.domain.HiddenObjectsGameStatus;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "api/v1/HiddenObjectsService",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class HiddenObjectsController extends ControllerBase {
    
    @Autowired
    private HiddenObjectsApiService hiddenObjectsService;

    @Autowired
    private HiddenObjectsGameStatus hiddenObjectsGameStatus;

    @Autowired
    private SelectedPet selectedPet;

    @PostMapping("joinGame")
    public HiddenObjectsGame joinGame(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid
            JoinHiddenObjectsGameArg joinHiddenObjectsGameArg)
            throws ServiceException {
        return hiddenObjectsService.joinGame(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                hiddenObjectsGameStatus, joinHiddenObjectsGameArg);
    }

    @GetMapping("getGameInfo")
    public HiddenObjectsGame getGameInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
            throws ServiceException {
        return hiddenObjectsService.getGameInfo(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                hiddenObjectsGameStatus);
    }

    @PostMapping("collectObject")
    public HiddenObjectsGame collectObject(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid CollectObjectArg collectObjectArg)
            throws ServiceException {
        return hiddenObjectsService.collectObject(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                hiddenObjectsGameStatus, collectObjectArg);
    }

    @PostMapping("startGame")
    public HiddenObjectsGame startGame(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
            throws ServiceException {
        return hiddenObjectsService.startGame(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                hiddenObjectsGameStatus);
    }

    @PostMapping("leaveGame")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveGame(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        hiddenObjectsService.leaveGame(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                hiddenObjectsGameStatus);
    }
}
