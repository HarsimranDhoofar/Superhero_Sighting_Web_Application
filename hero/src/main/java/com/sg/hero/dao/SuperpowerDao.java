/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Superhero;
import com.sg.hero.dto.Superpower;
import java.util.List;

/**
 *
 * @author Harsimran Dhoofar
 */
public interface SuperpowerDao {

    public List<Superpower> getAllSuperHeros();

    public Superpower addSuperpower(Superpower superpower);

    public void deleteSuperPowerById(int id);

    public Superpower getSuperpowerById(int id);
    
    public void updateSuperpower(Superpower superpower);
    public List<Superpower> getSuperPowerBySuperhero(Superhero superhero);
}
