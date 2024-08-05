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
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import java.util.Date;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JacksonConfig {
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(Date.class, new CustomDateSerializer());
//        module.addDeserializer(Date.class, new CustomDateDeserializer());
//        mapper.registerModule(module);
//        mapper.registerModule(new JavaTimeModule());
//        return mapper;
//    }
//}
