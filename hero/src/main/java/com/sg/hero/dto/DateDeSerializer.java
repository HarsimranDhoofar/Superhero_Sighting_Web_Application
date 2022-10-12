/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.hero.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Harsimran Dhoofar
 */
public class DateDeSerializer extends StdDeserializer<Date>{
     public DateDeSerializer() {
        super(Date.class);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        String value = p.readValueAs(String.class);
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (DateTimeParseException e) {
            //throw an 
        } catch (ParseException ex) {
             Logger.getLogger(DateDeSerializer.class.getName()).log(Level.SEVERE, null, ex);
         }
        return null;
    }
    
}
