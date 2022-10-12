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
public class Superhero {
    
     private int superhero_id;
     @NotBlank(message = "Superhero name cannot be empty.")
     @Size(max = 30, message="Superhero name max size is 30.")
    private String name;
     @NotBlank(message = "Superhero description must not be empty.")
     @Size(max = 30, message="Superhero description max size is 30.")
    private String description;
     @NotNull(message = "Please add and select a superpower. Can not be empty.")
    private List<Superpower>  superpowers;

    public Superhero() {
    }

    public Superhero(int superhero_id, String name, String description, List<Superpower> superpowers) {
        this.superhero_id = superhero_id;
        this.name = name;
        this.description = description;
        this.superpowers = superpowers;
    }

    public int getSuperhero_id() {
        return superhero_id;
    }

    public void setSuperhero_id(int superhero_id) {
        this.superhero_id = superhero_id;
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

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
        this.superpowers = superpowers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.superhero_id;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.superpowers);
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
        final Superhero other = (Superhero) obj;
        if (this.superhero_id != other.superhero_id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.superpowers, other.superpowers);
    }

    
   
  
    
}
