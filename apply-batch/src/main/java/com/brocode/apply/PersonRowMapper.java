package com.brocode.apply;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonRowMapper implements RowMapper<Person> {
    /**
     * @param rs - result set from database
     * @param rowNum - row number
     * @return person - resulting person
     * @throws SQLException - thrown when resiltresultset cannot be retrieved
     */
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getInt("age"));
    }
}
