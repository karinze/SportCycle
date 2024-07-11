package fpt.aptech.client.convert;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fpt.aptech.client.models.OrderItems;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Manh_Chien
 */
public class StringToVerificationConverterOrderItems implements Converter<Integer, OrderItems>{

    @Override
    public OrderItems convert(Integer orderItemsid) {
        return new OrderItems(orderItemsid);
    }
    
}
