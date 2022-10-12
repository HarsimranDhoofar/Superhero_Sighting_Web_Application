/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.controller;

import com.sg.hero.dao.LocationDao;
import com.sg.hero.dao.OrganizationDao;
import com.sg.hero.dao.SightingDao;
import com.sg.hero.dao.SuperheroDao;
import com.sg.hero.dao.SuperpowerDao;
import com.sg.hero.dto.Location;
import com.sg.hero.dto.Organization;
import com.sg.hero.dto.Sighting;
import com.sg.hero.dto.Superhero;
import com.sg.hero.dto.Superpower;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Harsimran Dhoofar
 */
@Controller
public class SightingController {
    
    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    
   @Autowired
   LocationDao locationDao;

   @Autowired
   SuperheroDao superheroDao;

   @Autowired
   OrganizationDao organizationDao; 
   
   @Autowired
   SightingDao  sightingDao;
   
   @Autowired
   SuperpowerDao superpowerDao;
    
   @GetMapping("sightings")
        public String displaySightings(Model model) {
        List<Sighting> sighting = sightingDao.getAllSighting();
        for(int i = 0 ; i< sighting.size(); i++){
            sighting.get(i).setSuperheros(superheroDao.getSuperheroById(sighting.get(i).getSuperhero_id()));
            sighting.get(i).setLocations(locationDao.getLocationById(sighting.get(i).getLocation_id()));
        }
        List<Superhero> superheros = superheroDao.getAllSuperHeros();
        List<Location> locations = locationDao.getAllLocation();
        model.addAttribute("sightings", sighting);
        model.addAttribute("superhero", superheros);
        model.addAttribute("location", locations);
        model.addAttribute("errors", violations);
        model.addAttribute("dateError", null);
        return "sightings";
        
        }  
        
     @PostMapping("addSighting")
        public String addLocation(HttpServletRequest request, Model model) throws ParseException {
        int superhero_id = Integer.parseInt(request.getParameter("superhero_id"));
        int location_id = Integer.parseInt(request.getParameter("location_id"));
        String date = request.getParameter("date");
         LocalDate dt = LocalDate.parse(date); 
        Sighting sighting = new Sighting();
        sighting.setSuperhero_id(superhero_id);
        sighting.setLocation_id(location_id);
        sighting.setDate(dt);
       // sightingDao.addSighting(sighting);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(sighting);
          if(violations.isEmpty()) {
            sightingDao.addSighting(sighting);
            
         }
          model.addAttribute("errors", violations);
        return "redirect:/sightings";
    }
        
      @GetMapping("sightingDetail")
        public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        Superhero superhero = superheroDao.getSuperheroById(sighting.getSuperhero_id());
        Location location = locationDao.getLocationById(sighting.getLocation_id());
        List<Sighting> sightingsBydate = sightingDao.getSightingbyPDate(sighting.getDate());
        for(int i = 0 ; i< sightingsBydate.size(); i++){
            sightingsBydate.get(i).setSuperheros(superheroDao.getSuperheroById(sightingsBydate.get(i).getSuperhero_id()));
            sightingsBydate.get(i).setLocations(locationDao.getLocationById(sightingsBydate.get(i).getLocation_id()));
        }
        model.addAttribute("sighting", sighting);
        model.addAttribute("superhero", superhero);
        model.addAttribute("location", location);
        model.addAttribute("sightingsBydate", sightingsBydate);
        return "sightingDetail";
    }
        
      @GetMapping("editSighting")
        public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        List<Superhero> superheros = superheroDao.getAllSuperHeros();
        List<Location> locations = locationDao.getAllLocation();
        model.addAttribute("sighting", sighting);
        model.addAttribute("superheros", superheros);
        model.addAttribute("locations", locations);
        return "editSighting";
    } 
    
        @PostMapping("editSighting")
        public String performEditSighting(HttpServletRequest request) throws ParseException {
        int superhero_id = Integer.parseInt(request.getParameter("superhero_id"));
        int location_id = Integer.parseInt(request.getParameter("location_id"));
        String date = request.getParameter("date");
        LocalDate dt = LocalDate.parse(date);
        Sighting sighting = new Sighting();
        sighting.setSuperhero_id(superhero_id);
        sighting.setLocation_id(location_id);
        sighting.setDate(dt);
        sightingDao.updateSighting(sighting);
        
        return "redirect:/sightings";
    } 
        
        @GetMapping("deleteSightingConfirm")
        public String deleteSightingConfirm(HttpServletRequest request, Model model) {
            int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        Superhero superhero = superheroDao.getSuperheroById(sighting.getSuperhero_id());
        Location location = locationDao.getLocationById(sighting.getLocation_id());
        List<Sighting> sightingsBydate = sightingDao.getSightingbyPDate(sighting.getDate());
        for(int i = 0 ; i< sightingsBydate.size(); i++){
            sightingsBydate.get(i).setSuperheros(superheroDao.getSuperheroById(sightingsBydate.get(i).getSuperhero_id()));
            sightingsBydate.get(i).setLocations(locationDao.getLocationById(sightingsBydate.get(i).getLocation_id()));
        }
        model.addAttribute("sighting", sighting);
        model.addAttribute("superhero", superhero);
        model.addAttribute("location", location);
        model.addAttribute("sightingsBydate", sightingsBydate);
        
        return "deleteSightingConfirm";
    }
        
           @GetMapping("deleteSighting")
        public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSightingById(id);
        
        return "redirect:/sightings";
    }
        
        
         @GetMapping("homepage")
        public String displayhomePage(Model model) {
        List<Sighting> sighting = sightingDao.getSightingbyDate();
        for(int i = 0 ; i< sighting.size(); i++){
            sighting.get(i).setSuperheros(superheroDao.getSuperheroById(sighting.get(i).getSuperhero_id()));
            sighting.get(i).setLocations(locationDao.getLocationById(sighting.get(i).getLocation_id()));
        }
        List<Superhero> superheros = superheroDao.getAllSuperHeros();
        List<Location> locations = locationDao.getAllLocation();
        model.addAttribute("sightings", sighting);
        model.addAttribute("superhero", superheros);
        model.addAttribute("location", locations);
            return "homepage";
        
 
       
        
        }  
}
