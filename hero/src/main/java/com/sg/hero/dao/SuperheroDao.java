/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Location;
import com.sg.hero.dto.Organization;
import com.sg.hero.dto.Superhero;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Harsimran Dhoofar
 */
public interface SuperheroDao {
    Superhero getSuperheroById(int id);
    List<Superhero> getAllSuperHeros();
    Superhero addSuperhero(Superhero student);
    List<Superhero> getAllSuperherosByLocation(Location location);
    void updateSuperhero(Superhero student);
    void deleteSuperheroById(int id);
    void addSighting(Superhero superhero, Location location, Timestamp date);
    List<Superhero> getSuperHeroByDate(Timestamp date);
    List<Superhero> getAllSuperherosByOrganization(Organization organization);
}
