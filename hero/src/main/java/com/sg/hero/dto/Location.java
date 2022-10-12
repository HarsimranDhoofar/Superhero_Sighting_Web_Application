/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Harsimran Dhoofar
 */
public class Location {
    private int location_id;
    @NotBlank(message = "Location name cannot be empty.")
     @Size(max = 30, message="Location name max size is 30.")
    private String name;
    @NotBlank(message = "Description name cannot be empty.")
     @Size(max = 30, message="Description name max size is 30.")
    private String description;
    @NotBlank(message = "Address name cannot be empty.")
     @Size(max = 30, message="Address name max size is 30.")
    private String address;
     @NotBlank(message = "Latitude cannot be empty.")
     @DecimalMin(value = "-90.0000000", message = "Minimum latitude value is -90.0000000")
     @DecimalMax(value = "90.0000000", message = "Maximum latitude value is 90.0000000")
    private String latitude;
     @NotBlank(message = "Longitude cannot be empty.")
     @DecimalMin(value = "-180.0000000", message = "Minimum Longitude value is -180.0000000")
     @DecimalMax(value = "180.0000000", message = "Maximum Longitude value is 180.0000000")
    private String longitude;
    private List<Superhero> superhero;
    
    
     public Location() {
    }

    public Location(int location_id, String name, String description, String address, String latitude, String longitude, List<Superhero> superhero) {
        this.location_id = location_id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.superhero = superhero;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Superhero> getSuperhero() {
        return superhero;
    }

    public void setSuperhero(List<Superhero> superhero) {
        this.superhero = superhero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.location_id;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.address);
        hash = 71 * hash + Objects.hashCode(this.latitude);
        hash = 71 * hash + Objects.hashCode(this.longitude);
        hash = 71 * hash + Objects.hashCode(this.superhero);
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
        final Location other = (Location) obj;
        if (this.location_id != other.location_id) {
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
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return Objects.equals(this.superhero, other.superhero);
    }

  
}
