package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.service.domain.LastRegisteredUser;

public interface JdbcReportDao {

    List<LastRegisteredUser> findLastRegisteredUsers(int start, int limit);
}
