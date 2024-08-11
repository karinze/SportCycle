/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.models;


import java.math.BigDecimal;

import java.util.Date;


/**
 *
 * @author Manh_Chien
 */

public class Items {
    public int item_id;
    public String name;
    public String brand;
    public String description;
    public BigDecimal price;
    public int stock;
    public int rentalquantity;
    public String type;
    public String image;
    public boolean is_visible;
    public Date created_dt;
    

    
    
    
    public Items() {
    }

    public Items(int item_id) {
        this.item_id = item_id;
        
    }

    public Items( String name, String brand, String description, BigDecimal price, int stock, int rentalquantity, String type, String image, boolean is_visible, Date created_dt) {
        
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.rentalquantity = rentalquantity;
        this.type = type;
        this.image = image;
        this.is_visible = is_visible;
        this.created_dt = created_dt;
    }

    public Items(int item_id, String name, String brand, String description, BigDecimal price, int stock, int rentalquantity, String type, String image, boolean is_visible, Date created_dt) {
        this.item_id = item_id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.rentalquantity = rentalquantity;
        this.type = type;
        this.image = image;
        this.is_visible = is_visible;
        this.created_dt = created_dt;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRentalquantity() {
        return rentalquantity;
    }

    public void setRentalquantity(int rentalquantity) {
        this.rentalquantity = rentalquantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }
    

    

    

    
    
    
}
