/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.convert;

import fpt.aptech.client.models.Users;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Manh_Chien
 */
public class StringToVerificationConverterUser implements Converter<UUID, Users>{

    @Override
    public Users convert(UUID userid) {
        return new Users(userid);
    }

    
}