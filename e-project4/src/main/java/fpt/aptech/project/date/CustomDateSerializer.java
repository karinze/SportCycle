///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package fpt.aptech.project.date;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class CustomDateSerializer extends JsonSerializer<Date> {
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//
//    @Override
//    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeString(dateFormat.format(date));
//    }
//}
//
