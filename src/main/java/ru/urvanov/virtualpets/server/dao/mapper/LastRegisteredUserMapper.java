package ru.urvanov.virtualpets.server.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.urvanov.virtualpets.server.dao.domain.LastRegisteredUser;

public class LastRegisteredUserMapper implements RowMapper<LastRegisteredUser> {

    @Override
    public LastRegisteredUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        LastRegisteredUser lastRegisteredUser = new LastRegisteredUser();
        lastRegisteredUser.setRegistrationDate(rs.getDate("registration_date"));
        lastRegisteredUser.setName(rs.getString("name"));
        lastRegisteredUser.setPetsCount(rs.getLong("pets_count"));
        return lastRegisteredUser;
    }

}
