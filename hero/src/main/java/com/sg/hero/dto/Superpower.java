/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Harsimran Dhoofar
 */
public class Superpower {
    private int superpower_id;
    @NotBlank(message = "Superpower name cannot be empty.")
     @Size(max = 30, message="Superpower name max size is 30.")
    private String name;
    @NotBlank(message = "Superpower description cannot be empty.")
     @Size(max = 30, message="Superpower description max size is 30.")
    private String description;

    public Superpower() {
    }

    public Superpower(int superpower_id, String name, String description) {
        this.superpower_id = superpower_id;
        this.name = name;
        this.description = description;
    }

    public int getSuperpower_id() {
        return superpower_id;
    }

    public void setSuperpower_id(int superpower_id) {
        this.superpower_id = superpower_id;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.superpower_id;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.description);
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
        final Superpower other = (Superpower) obj;
        if (this.superpower_id != other.superpower_id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }
    
    
}
