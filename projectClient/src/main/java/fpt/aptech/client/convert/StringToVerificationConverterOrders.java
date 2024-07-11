package fpt.aptech.client.convert;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import fpt.aptech.client.models.Orders;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Manh_Chien
 */
public class StringToVerificationConverterOrders implements Converter<Integer, Orders>{

    @Override
    public Orders convert(Integer ordersid) {
        return new Orders(ordersid);
    }
    
}
