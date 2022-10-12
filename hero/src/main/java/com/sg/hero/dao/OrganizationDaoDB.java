/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dao.LocationDaoDB.LocationMapper;
import com.sg.hero.dao.SuperheroDaoDB.SuperheroMapper;
import com.sg.hero.dto.Location;
import com.sg.hero.dto.Organization;
import com.sg.hero.dto.Superhero;
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
public  class OrganizationDaoDB implements OrganizationDao{
     @Autowired
        JdbcTemplate jdbc;
       
    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE organization_id = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setSuperheros(getSuperheroForOrganization(id));
            return organization;
        } catch(DataAccessException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private Location getLocationForOrganization(int id) {
        final String SELECT_LOCATION_FOR_ORGANIZATION = "SELECT t.* FROM location t "
                + "JOIN organization c ON c.location_id = t.location_id WHERE c.organization_id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_ORGANIZATION, new LocationMapper(), id);
    }

     private List<Superhero> getSuperheroForOrganization(int id) {
        final String SELECT_STUDENTS_FOR_COURSE = "SELECT s.* FROM superhero s "
                + "JOIN organization_superhero cs ON cs.superhero_id = s.superhero_id WHERE cs.organization_id = ?";
        return jdbc.query(SELECT_STUDENTS_FOR_COURSE, new SuperheroMapper(), id);
    } 

    
    @Override
    public List<Organization> getAllOrganization() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateSuperhero(organizations);
        return organizations;
    }
    private void associateSuperhero(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setSuperheros(getSuperheroForOrganization(organization.getOrganization_id()));
        }
    }
    
    
    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
             final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, address) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganization_id(newId);
        insertOrganizationSuperhero(organization);
        return organization;
    }
    
     private void insertOrganizationSuperhero(Organization organization) {
        final String INSERT_ORGANIZATION_SUPERHERO = "INSERT INTO "
                + "organization_superhero(superhero_id, organization_id) VALUES(?,?)";
        for(Superhero superhero : organization.getSuperheros()) {
            jdbc.update(INSERT_ORGANIZATION_SUPERHERO, 
                    superhero.getSuperhero_id(),
                    organization.getOrganization_id());
        }
        
     }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
       final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, "
                + "address = ? WHERE organization_id = ?";
        jdbc.update(UPDATE_ORGANIZATION, 
                organization.getName(), 
                organization.getDescription(),
                organization.getAddress(),
                organization.getOrganization_id());
        
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM organization_superhero WHERE organization_id = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, organization.getOrganization_id());
        insertOrganizationSuperhero(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM organization_superhero WHERE organization_id = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, id);
        
        
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE organization_id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }


    @Override
    public List<Organization> getOrganizationForSuperhero(Superhero superhero) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT c.* FROM organization c JOIN "
                + "organization_superhero cs ON cs.organization_id = c.organization_id WHERE cs.superhero_id = ?";
        List<Organization> organization = jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO, 
                new OrganizationMapper(), superhero.getSuperhero_id());
        associateSuperhero(organization);
        return organization;
    }
    
     public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganization_id(rs.getInt("organization_id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            return organization;
        }
    }
}
