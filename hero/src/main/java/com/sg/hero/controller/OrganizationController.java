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
public class OrganizationController {
    
    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    
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
    
     @GetMapping("organizations")
        public String displayOrganizations(Model model) {
        List<Superhero> superheros = superheroDao.getAllSuperHeros();   
        List<Organization> organizations = organizationDao.getAllOrganization();
        model.addAttribute("superheros", superheros);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
        return "organizations";
        
        }  
    
        @PostMapping("addOrganization")
        public String addOrganization(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String[] superhero_Ids = request.getParameterValues("superhero_id");
        List<Superhero> superheros = new ArrayList<>();
        if(superhero_Ids != null){
        for(String superhero_Id : superhero_Ids){
            superheros.add(superheroDao.getSuperheroById(Integer.parseInt(superhero_Id)));
        }
        }
        Organization organization = new Organization();
        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setSuperheros(superheros);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(organization);
          if(violations.isEmpty()) {
            organizationDao.addOrganization(organization);
            
         }
        
        model.addAttribute("errors", violations);
        return "redirect:/organizations";
    }
    
        
       @GetMapping("organizationDetail")
        public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
        
       
       @GetMapping("editOrganization")
        public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Superhero> superheros = superheroDao.getAllSuperHeros();
        model.addAttribute("organizations", organization);
        model.addAttribute("superheros", superheros);
        model.addAttribute("errors", violations);
        return "editOrganization";
    }
        
      @PostMapping("editOrganization")
        public String performEditOrganization(Organization organization, HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String[] superhero_Ids = request.getParameterValues("superhero_id");
        List<Superhero> superheros = new ArrayList<>();
        for(String superhero_Id : superhero_Ids){
            superheros.add(superheroDao.getSuperheroById(Integer.parseInt(superhero_Id)));
        }
        model.addAttribute("organizations", organization);
        model.addAttribute("superheros", superheros);
        organization.setOrganization_id(Integer.parseInt(request.getParameter("id")));
        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setSuperheros(superheros);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
         violations = validate.validate(organization);
          if(violations.isEmpty()) {
            organizationDao.updateOrganization(organization);
            return "redirect:/organizations";
         }
        
        model.addAttribute("errors", violations);
        return "editOrganization";
        
        
    }
        
        @GetMapping("deleteOrganizationConfirm")
        public String deleteOrganizationConfirm(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "deleteOrganizationConfirm";
    }
        
     @GetMapping("deleteOrganization")
        public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
}
