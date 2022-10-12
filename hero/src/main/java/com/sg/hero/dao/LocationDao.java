/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Location;
import com.sg.hero.dto.Superhero;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Harsimran Dhoofar
 */
public interface LocationDao {
    Location getLocationById(int id);
    List<Location> getAllLocation();
    Location addLocation(Location teacher);
    void updateLocation(Location teacher);
    void deleteLocationById(int id);
    List<Location> getLocationByDate(Timestamp date);
    List<Location> getLocationForSuperhero(Superhero superhero);
}
