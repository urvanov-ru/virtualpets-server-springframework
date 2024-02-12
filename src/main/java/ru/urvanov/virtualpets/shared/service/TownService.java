package ru.urvanov.virtualpets.shared.service;

import ru.urvanov.virtualpets.shared.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

public interface TownService {
    GetTownInfoResult getTownInfo() throws DaoException, ServiceException;
}
