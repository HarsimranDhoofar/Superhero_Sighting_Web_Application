/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dao.SuperpowerDaoDB.SuperPowerMapper;
import com.sg.hero.dto.Location;
import com.sg.hero.dto.Organization;
import com.sg.hero.dto.Superhero;
import com.sg.hero.dto.Superpower;
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
public class SuperheroDaoDB implements SuperheroDao{
     @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE superhero_id = ?";
            Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            superhero.setSuperpowers(getSuperPowerBySuperhero(id));
            return superhero;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    @Override
    public List<Superhero> getAllSuperHeros() {
        final String SELECT_ALL_SUPERHERO = "SELECT * FROM superhero";
        List<Superhero> superheros = jdbc.query(SELECT_ALL_SUPERHERO, new SuperheroMapper());
        associateSuperpower(superheros);
        return superheros;
    }
    
      public List<Superpower> getSuperPowerBySuperhero(int superhero){
        final String SELECT_ORGANIZATION_FOR_SUPERHERO = "SELECT s.* FROM superpower s JOIN "
                + "superpower_superhero ls ON ls.superpower_id = s.superpower_id WHERE ls.superhero_id = ?";
        return jdbc.query(SELECT_ORGANIZATION_FOR_SUPERHERO, 
                new SuperPowerMapper(), superhero);
       
    }
       private void associateSuperpower(List<Superhero> superheros) {
        for (Superhero superhero : superheros) {
            superhero.setSuperpowers(getSuperPowerBySuperhero(superhero.getSuperhero_id()));
        }
    }
    
    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setSuperhero_id(newId);
        insertSuperpowerSuperhero(superhero);
        return superhero;
    }
    
    private void insertSuperpowerSuperhero(Superhero superhero) {
        final String INSERT_ORGANIZATION_SUPERHERO = "INSERT INTO "
                + "superpower_superhero(superpower_id, superhero_id) VALUES(?,?)";
        for(Superpower superpower : superhero.getSuperpowers()) {
            jdbc.update(INSERT_ORGANIZATION_SUPERHERO, 
                    superpower.getSuperpower_id(),
                    superhero.getSuperhero_id());
        }
        
     }
    @Override
    @Transactional
   public void addSighting(Superhero superhero, Location location, Timestamp date){
        final String INSERT_LOCATION_SUPERHERO = "INSERT INTO "
                + "location_superhero(superhero_id, location_id, date) VALUES(?,?,?)";
         jdbc.update(INSERT_LOCATION_SUPERHERO,
                superhero.getSuperhero_id(),
                location.getLocation_id(),
                date);
   }
   
      @Override
   public List<Superhero> getSuperHeroByDate(Timestamp date){
       final String SELECT_SIGHTING_FOR_DATE = "SELECT s.* FROM superhero s JOIN "
                + "location_superhero ls ON ls.superhero_id = s.superhero_id WHERE ls.date = ?";
       List<Superhero> superheros = jdbc.query(SELECT_SIGHTING_FOR_DATE, new SuperheroMapper(), date);
       return superheros;
   }
   
       @Override
    public List<Superhero> getAllSuperherosByLocation(Location location){
        final String SELECT_LOCATION_FOR_SUPERHERO = "SELECT DISTINCT s.* FROM superhero s JOIN "
                + "sighting ls ON ls.superhero_id = s.superhero_id WHERE ls.location_id = ?";
        List<Superhero> superheros = jdbc.query(SELECT_LOCATION_FOR_SUPERHERO, 
                new SuperheroMapper(), location.getLocation_id());
        return superheros;
    }
    
           @Override
    public List<Superhero> getAllSuperherosByOrganization(Organization organization){
        final String SELECT_ORGANIZATION_FOR_SUPERHERO = "SELECT s.* FROM superhero s JOIN "
                + "organization_superhero ls ON ls.superhero_id = s.superhero_id WHERE ls.organization_id = ?";
        List<Superhero> superheros = jdbc.query(SELECT_ORGANIZATION_FOR_SUPERHERO, 
                new SuperheroMapper(), organization.getOrganization_id());
        return superheros;
    }
    
    @Override
    public void updateSuperhero(Superhero superhero) {
    final String UPDATE_STUDENT = "UPDATE superhero SET name = ?, description = ? "
                + "WHERE superhero_id = ?";
        jdbc.update(UPDATE_STUDENT,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperhero_id());
        
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM superpower_superhero WHERE superhero_id = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, superhero.getSuperhero_id());
        insertSuperpowerSuperhero(superhero);
    }
    

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERPOWER_SUPERHERO = "DELETE FROM superpower_superhero WHERE superhero_id = ?";
        jdbc.update(DELETE_SUPERPOWER_SUPERHERO, id);
        final String DELETE_COURSE_STUDENT = "DELETE FROM sighting WHERE superhero_id = ?";
        jdbc.update(DELETE_COURSE_STUDENT, id);
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM organization_superhero WHERE superhero_id = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, id);
        final String DELETE_STUDENT = "DELETE FROM superhero WHERE superhero_id = ?";
        jdbc.update(DELETE_STUDENT, id);
    }
    
        public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setSuperhero_id(rs.getInt("superhero_id"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            

            return superhero;
        }
    }
}
