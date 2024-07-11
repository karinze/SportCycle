/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Configuration.java to edit this template
 */
package fpt.aptech.client.config;


import fpt.aptech.client.convert.StringToVerificationConverterItem;
import fpt.aptech.client.convert.StringToVerificationConverterOrderItems;
import fpt.aptech.client.convert.StringToVerificationConverterOrders;
import fpt.aptech.client.convert.StringToVerificationConverterUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Manh_Chien
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(new StringToVerificationConverterItem());
        registry.addConverter(new StringToVerificationConverterUser());
        registry.addConverter(new StringToVerificationConverterOrderItems());
        registry.addConverter(new StringToVerificationConverterOrders());
        
    }
}
