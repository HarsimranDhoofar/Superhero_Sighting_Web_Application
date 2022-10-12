/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Harsimran Dhoofar
 */
public class Organization {
     private int organization_id;
     @NotBlank(message = "Organization name cannot be empty.")
     @Size(max = 30, message="Organization name max size is 30.")
    private String name;
     @NotBlank(message = "Organization description cannot be empty.")
     @Size(max = 30, message="Organization description max size is 30.")
    private String description;
     @NotBlank(message = "Organization address cannot be empty.")
     @Size(max = 30, message="Organization address max size is 30.")
    private String address;
     @NotNull(message = "Please add and select one or more superheros. Can not be empty.")
    private List<Superhero> superheros;

    public Organization(int organization_id, String name, String description, String address, List<Superhero> superheros) {
        this.organization_id = organization_id;
        this.name = name;
        this.description = description;
        this.address = address;
       
        this.superheros = superheros;
    }

    public Organization() {
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    

    public List<Superhero> getSuperheros() {
        return superheros;
    }

    public void setSuperheros(List<Superhero> superheros) {
        this.superheros = superheros;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.organization_id;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.address);
       
        hash = 29 * hash + Objects.hashCode(this.superheros);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.organization_id != other.organization_id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return Objects.equals(this.superheros, other.superheros);
    }

    
    
}
