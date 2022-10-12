/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Sighting;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Harsimran Dhoofar
 */
public interface SightingDao {

    List<Sighting> getAllSighting();
    public Sighting getSightingById(int id);
    public void addSighting(Sighting sighting);

    public void updateSighting(Sighting sighting);

    public void deleteSightingById(int id);
    public List<Sighting> getSightingbyDate();
    public List<Sighting> getSightingbyPDate(LocalDate date);
}
