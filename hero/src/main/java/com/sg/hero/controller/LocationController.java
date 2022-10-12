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
import com.sg.hero.dto.Superhero;
import com.sg.hero.dto.Superpower;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Harsimran Dhoofar
 */
@Controller
public class LocationController {
    
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
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
    
       @GetMapping("locations")
        public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocation();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "locations";
        
        }  
        
       @PostMapping("addLocation")
        public String addLocation(HttpServletRequest request,Model model) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(location);
          if(violations.isEmpty()) {
            locationDao.addLocation(location);
         }
        model.addAttribute("errors", violations);
        
        
        return "redirect:/locations";
    }
        
        @GetMapping("locationDetail")
        public String locationDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        List<Superhero> superheros = superheroDao.getAllSuperherosByLocation(location);
        model.addAttribute("superheros", superheros);
        model.addAttribute("location", location);
        return "locationDetail";
    }
        
        @GetMapping("editLocation")
        public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        model.addAttribute("locations", location);
        model.addAttribute("errors", violations);
        return "editLocation";
    } 
    
        @PostMapping("editLocation")
        public String performEditLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        model.addAttribute("locations", location);
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(location);
          if(violations.isEmpty()) {
            locationDao.updateLocation(location);
            return "redirect:/locations";
         }
        model.addAttribute("errors", violations);
       
        return "editLocation";
        
    } 
        
      
        @GetMapping("deleteLocationConfirm")
        public String deleteLocationConfirm(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        List<Superhero> superheros = superheroDao.getAllSuperherosByLocation(location);
        model.addAttribute("superheros", superheros);
        model.addAttribute("location", location);
        
        return "deleteLocationConfirm";
    }
        @GetMapping("deleteLocation")
        public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);
        
        return "redirect:/locations";
    }
}
