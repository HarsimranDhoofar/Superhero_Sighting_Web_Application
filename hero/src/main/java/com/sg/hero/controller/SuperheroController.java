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
import com.sg.hero.dto.Superhero;
import com.sg.hero.dto.Superpower;
import java.util.ArrayList;
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
public class SuperheroController {
   Set<ConstraintViolation<Superhero>> violations = new HashSet<>();
   
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
   
   
       @GetMapping("superheros")
        public String displaySuperheros(Model model) {
        List<Superhero> superheros = superheroDao.getAllSuperHeros();
        List<Superpower> superpowers = superpowerDao.getAllSuperHeros();
        model.addAttribute("superheros", superheros);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("errors", violations);
        return "superheros";
    }
         
      @PostMapping("addSuperhero")
        public String addSuperpower(HttpServletRequest request,Model model) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String[] superpower_Ids = request.getParameterValues("superpower_id");
        List<Superpower> superpowers = new ArrayList<>();
        if(superpower_Ids != null){
        for(String superpower_Id : superpower_Ids){
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpower_Id)));
        }
        }
          System.out.println(superpowers.size());
        Superhero superhero = new Superhero();
        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setSuperpowers(superpowers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(superhero);
          if(violations.isEmpty()) {
            superheroDao.addSuperhero(superhero);
         }
        model.addAttribute("errors", violations);
        
        return "redirect:/superheros";
    }
        
      @GetMapping("superheroDetail")
        public String superheroDetail(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id); 
       
        List<Organization> organization = organizationDao.getOrganizationForSuperhero(superhero);
        List<Location> locations = locationDao.getLocationForSuperhero(superhero);
        model.addAttribute("superhero", superhero);
        
        model.addAttribute("organization", organization);
        model.addAttribute("locations", locations);
        return "superheroDetail";
    }
        
       @GetMapping("editSuperhero")
        public String editSuperhero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Superpower> superpowers = superpowerDao.getAllSuperHeros();
        model.addAttribute("superheros", superhero);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("errors", violations);
        return "editSuperhero";
    }   
        
       @PostMapping("editSuperhero")
        public String performEditSuperhero(Superhero superhero, HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String[] superpower_Ids = request.getParameterValues("superpower_id");
            List<Superpower> superpowers = new ArrayList<>();
            for(String superpower_Id : superpower_Ids){
                superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpower_Id)));
            }
            System.out.println(superpowers.size());
        superhero.setSuperhero_id(id);
        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setSuperpowers(superpowers);
        
      //  superhero.setSuperpower_id(Integer.parseInt(request.getParameter("superpower_id")));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(superhero);
         if(violations.isEmpty()) {
            superheroDao.updateSuperhero(superhero);
            return "redirect:/superheroDetail?id="+ id;
         }
        
        model.addAttribute("errors", violations);
        return "editSuperhero";
        
        
    } 
        
       @GetMapping("deleteSuperhero")
        public String deleteSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superheroDao.deleteSuperheroById(id);
        
        return "redirect:/superheros";
    }
        
         @GetMapping("deleteSuperheroConfirm")
        public String deleteSuperheroConfirm(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
       Superhero superhero = superheroDao.getSuperheroById(id); 
       
        List<Organization> organization = organizationDao.getOrganizationForSuperhero(superhero);
        List<Location> locations = locationDao.getLocationForSuperhero(superhero);
        model.addAttribute("superhero", superhero);
        
        model.addAttribute("organization", organization);
        model.addAttribute("locations", locations);
        
        return "deleteSuperheroConfirm";
    }
}
