package ru.urvanov.virtualpets.server.api.domain;

import java.util.List;

public record RefreshUsersOnlineResult(List<UserInfo> users) {
};
