/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dao;

import com.sg.hero.dto.Organization;
import com.sg.hero.dto.Superhero;
import java.util.List;

/**
 *
 * @author Harsimran Dhoofar
 */
public interface OrganizationDao {
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganization();
    Organization addOrganization(Organization course);
    void updateOrganization(Organization course);
    void deleteOrganizationById(int id);
    
 
    List<Organization> getOrganizationForSuperhero(Superhero student);
}
