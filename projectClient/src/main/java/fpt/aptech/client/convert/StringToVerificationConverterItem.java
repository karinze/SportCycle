package fpt.aptech.client.convert;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fpt.aptech.client.models.Items;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Manh_Chien
 */
public class StringToVerificationConverterItem implements Converter<Integer, Items>{

    @Override
    public Items convert(Integer id) {
        return new Items(id);
    }
    
}
