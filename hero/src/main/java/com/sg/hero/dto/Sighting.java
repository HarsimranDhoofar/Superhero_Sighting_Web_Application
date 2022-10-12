/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author Harsimran Dhoofar
 */
public class Sighting {
    private int sighting_id;
    @NotNull(message = "Superhero cannot be empty.")
    private int superhero_id;
    @NotNull(message = "Location cannot be empty.")
    private int location_id;
//   @JsonDeserialize(using = DateDeSerializer.class)
    //@Pattern(regexp="yyyy-MM-dd")
    @NotNull(message = "Date cannot be empty.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Sigting can only happen in past.")
    private LocalDate date;
//    @NotNull(message = "Superhero  cannot be empty.")
    private Superhero superheros;
//    @NotNull(message = "Superhero  cannot be empty.")
    private Location locations;
    

    public Sighting() {
    }

    public Sighting(int sighting_id, int superhero_id, int location_id, LocalDate date, Superhero superheros, Location locations) {
        this.sighting_id = sighting_id;
        this.superhero_id = superhero_id;
        this.location_id = location_id;
        this.date = date;
        this.superheros = superheros;
        this.locations = locations;
    }

    public int getSighting_id() {
        return sighting_id;
    }

    public void setSighting_id(int sighting_id) {
        this.sighting_id = sighting_id;
    }

    public int getSuperhero_id() {
        return superhero_id;
    }

    public void setSuperhero_id(int superhero_id) {
        this.superhero_id = superhero_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Superhero getSuperheros() {
        return superheros;
    }

    public void setSuperheros(Superhero superheros) {
        this.superheros = superheros;
    }

    public Location getLocations() {
        return locations;
    }

    public void setLocations(Location locations) {
        this.locations = locations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.sighting_id;
        hash = 17 * hash + this.superhero_id;
        hash = 17 * hash + this.location_id;
        hash = 17 * hash + Objects.hashCode(this.date);
        hash = 17 * hash + Objects.hashCode(this.superheros);
        hash = 17 * hash + Objects.hashCode(this.locations);
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
        final Sighting other = (Sighting) obj;
        if (this.sighting_id != other.sighting_id) {
            return false;
        }
        if (this.superhero_id != other.superhero_id) {
            return false;
        }
        if (this.location_id != other.location_id) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.superheros, other.superheros)) {
            return false;
        }
        return Objects.equals(this.locations, other.locations);
    }

    

    
   
    
}
