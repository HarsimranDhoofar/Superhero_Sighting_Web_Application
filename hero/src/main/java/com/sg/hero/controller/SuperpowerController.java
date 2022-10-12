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
import com.sg.hero.dto.Organization;
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
public class SuperpowerController {
    
    
    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();
    
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
   
   
     @GetMapping("superpowers")
        public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperHeros();
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("errors", violations);
        return "superpowers";
        
        }  
        
     @PostMapping("addSuperpower")
        public String addSuperpower(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(superpower);
          if(violations.isEmpty()) {
            superpowerDao.addSuperpower(superpower);
         }
        model.addAttribute("errors", violations);
        
        return "redirect:/superpowers";
    }
        
      @GetMapping("deleteSuperpower")
        public String deleteTeacher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerDao.deleteSuperPowerById(id);
        
        return "redirect:/superpowers";
    }
        
       @GetMapping("deleteSuperpowerConfirm")
        public String deleteSuperpowerConfirm(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
         
        model.addAttribute("superpower", superpower);
        
        return "deleteSuperpowerConfirm";
    }
       
       
      @GetMapping("editSuperpower")
        public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("errors", violations);
        model.addAttribute("superpowers", superpower);
        return "editSuperpower";
    }   
        
      @PostMapping("editSuperpower")
        public String performEditSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpowers", superpower);
        superpower.setName(request.getParameter("name"));
        superpower.setDescription(request.getParameter("description"));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(superpower);
          if(violations.isEmpty()) {
            superpowerDao.updateSuperpower(superpower);
            return "redirect:/superpowerDetail?id="+id;
         }
        model.addAttribute("errors", violations);
        return "editSuperpower";
        
        
    } 
        
        
     @GetMapping("superpowerDetail")
        public String superpowerDetail(Integer id, Model model) {
        Superpower superpower = superpowerDao.getSuperpowerById(id);
         
        model.addAttribute("superpower", superpower);
        return "superpowerDetail";
    }
}
