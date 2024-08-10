package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

public record RefreshUsersOnlineResult(List<UserInfo> users) {
};
