/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Location;
import com.sg.hero.dto.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Harsimran Dhoofar
 */
@Repository
public class LocationDaoDB implements LocationDao{
     @Autowired
   JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
      try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE location_id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocation() {
        final String SELECT_ALL_LOCATION = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATION, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, description, address, latitude, longitude) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocation_id(newId);
        return location;
    }

    @Override 
    public List<Location> getLocationForSuperhero(Superhero superhero){
      final String SELECT_LOCATION_FOR_SUPERHERO = "SELECT DISTINCT s.* FROM location s JOIN "
                + "sighting ls ON ls.location_id = s.location_id WHERE ls.superhero_id = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATION_FOR_SUPERHERO, 
                new LocationMapper(), superhero.getSuperhero_id());
        return locations;
    
}
    
        @Override
   public List<Location> getLocationByDate(Timestamp date){
       final String SELECT_SIGHTING_FOR_DATE = "SELECT s.* FROM location s JOIN "
                + "location_superhero ls ON ls.location_id = s.location_id WHERE ls.date = ?";
       List<Location> location = jdbc.query(SELECT_SIGHTING_FOR_DATE, new LocationMapper(), date);
       return location;
   }
   
   
   
    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, address = ?, latitude = ?, longitude = ? "
                + "WHERE location_id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocation_id());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_ORGANIZATION = "DELETE FROM sighting WHERE location_id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
        
        final String DELETE_LOCATION = "DELETE FROM location WHERE location_id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }
    

 public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocation_id(rs.getInt("location_id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getString("latitude"));
            location.setLongitude(rs.getString("longitude"));
            return location;
        }
    } 
}
