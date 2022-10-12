/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Organization;
import com.sg.hero.dto.Superhero;
import com.sg.hero.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SuperpowerDaoDB implements SuperpowerDao {

     @Autowired
    JdbcTemplate jdbc;
     
     
    @Override
    public List<Superpower> getAllSuperHeros() {
         final String SELECT_ALL_SUPERPOWER = "SELECT * FROM superpower";
         return jdbc.query(SELECT_ALL_SUPERPOWER, new SuperPowerMapper());
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        final String INSERT_SUPERPOWER = "INSERT INTO superpower(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_SUPERPOWER,
                superpower.getName(),
                superpower.getDescription());
                
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setSuperpower_id(newId);
        return superpower;
    }

    @Override
    @Transactional
    public void deleteSuperPowerById(int id) {
          
        final String DELETE_SUPERPOWER_SUPERHERO = "DELETE FROM superpower_superhero WHERE superpower_id = ?";
        jdbc.update(DELETE_SUPERPOWER_SUPERHERO, id);
        final String DELETE_SUPERPOWER = "DELETE FROM superpower WHERE superpower_id = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM superpower WHERE superpower_id = ?";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperPowerMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
 
    @Override 
      public List<Superpower> getSuperPowerBySuperhero(Superhero superhero){
        final String SELECT_ORGANIZATION_FOR_SUPERHERO = "SELECT s.* FROM superpower s JOIN "
                + "superpower_superhero ls ON ls.superpower_id = s.superpower_id WHERE ls.superhero_id = ?";
        return jdbc.query(SELECT_ORGANIZATION_FOR_SUPERHERO, 
                new SuperPowerMapper(), superhero.getSuperhero_id());
       
    }
    
    @Override
     public void updateSuperpower(Superpower superpower) {
          final String UPDATE_SUPERPOWER = "UPDATE superpower SET name = ?, description = ?  WHERE superpower_id = ?";
           jdbc.update(UPDATE_SUPERPOWER,
                superpower.getName(),
                superpower.getDescription(),
                superpower.getSuperpower_id());
     }
    
       public static final class SuperPowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setSuperpower_id(rs.getInt("superpower_id"));
            superpower.setName(rs.getString("name"));
            superpower.setDescription(rs.getString("description"));

            return superpower;
        }
    }
}
