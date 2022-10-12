/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Sighting;
import com.sg.hero.dto.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Harsimran Dhoofar
 */
@Repository
public class SightingDaoDB implements SightingDao{
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public List<Sighting> getAllSighting(){
        final String SELECT_ALL_SIGHTING = "SELECT * FROM sighting";
        return jdbc.query(SELECT_ALL_SIGHTING, new SightingMapper());
    }

    @Override
    public void addSighting(Sighting sighting) {
        final String INSERT_LOCATION_SUPERHERO = "INSERT INTO "
                + "sighting(superhero_id, location_id, date) VALUES(?,?,?)";
         jdbc.update(INSERT_LOCATION_SUPERHERO,
                sighting.getSuperhero_id(),
                sighting.getLocation_id(),
                sighting.getDate());
    }
    
      @Override
       public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sighting_id = ?";
            return jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
       final String UPDATE_SIGHTING = "UPDATE sighting SET superhero_id = ?, location_id = ?, date = ? WHERE sighting_id = ?";
           jdbc.update(UPDATE_SIGHTING,
                sighting.getSuperhero_id(),
                sighting.getLocation_id(),
                sighting.getDate(),
                sighting.getSighting_id());
    }

    @Override
    public void deleteSightingById(int id) {
         final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sighting_id = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        
    }

    @Override
    public List<Sighting> getSightingbyDate() {
        final String GET_SIGHTING_BY_DATE = "Select * from sighting ORDER BY date DESC limit 10";
        List<Sighting> s = jdbc.query(GET_SIGHTING_BY_DATE,new SightingMapper());
        return s;
    }
    
    @Override
    public List<Sighting> getSightingbyPDate(LocalDate date) {
        final String GET_SIGHTING_BY_DATE = "Select * from sighting WHERE date = ? ";
        List<Sighting> s = jdbc.query(GET_SIGHTING_BY_DATE,new SightingMapper(), date);
        return s;
    }
    
     public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSighting_id(rs.getInt("sighting_id"));
            sighting.setSuperhero_id(rs.getInt("superhero_id"));
            sighting.setLocation_id(rs.getInt("location_id"));
            sighting.setDate(rs.getDate("date").toLocalDate());

            return sighting;
        }
    }
}
