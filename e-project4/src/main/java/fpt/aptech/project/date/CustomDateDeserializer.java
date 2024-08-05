///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package fpt.aptech.project.date;
//
///**
// *
// * @author Manh_Chien
// */
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class CustomDateDeserializer extends JsonDeserializer<Date> {
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//
//    @Override
//    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//        String dateStr = p.getText();
//        try {
//            return dateFormat.parse(dateStr);
//        } catch (ParseException e) {
//            throw new IOException(e);
//        }
//    }
//}
