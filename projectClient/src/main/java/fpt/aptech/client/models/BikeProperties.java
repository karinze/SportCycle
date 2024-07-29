/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.models;


import java.util.Date;


/**
 *
 * @author Manh_Chien
 */

public class BikeProperties {
    
    public int bike_property_id;
    
    
    public Items item;
    
    
    public String bike_size;
    
    public String bike_wheel_size;
    
    public String bike_color;
    
    public String bike_material;
    
    public String bike_brake_type;
    
    public Date created_dt;
    public BikeProperties() {
    }

    public BikeProperties(int bike_property_id) {
        this.bike_property_id = bike_property_id;
    }

    public BikeProperties(int bike_property_id, Items item, String bike_size, String bike_wheel_size, String bike_color, String bike_material, String bike_brake_type, Date created_dt) {
        this.bike_property_id = bike_property_id;
        this.item = item;
        this.bike_size = bike_size;
        this.bike_wheel_size = bike_wheel_size;
        this.bike_color = bike_color;
        this.bike_material = bike_material;
        this.bike_brake_type = bike_brake_type;
        this.created_dt = created_dt;
    }
    
    public BikeProperties( Items item, String bike_size, String bike_wheel_size, String bike_color, String bike_material, String bike_brake_type, Date created_dt) {
        this.item = item;
        this.bike_size = bike_size;
        this.bike_wheel_size = bike_wheel_size;
        this.bike_color = bike_color;
        this.bike_material = bike_material;
        this.bike_brake_type = bike_brake_type;
        this.created_dt = created_dt;
    }
    
    

    public int getBike_property_id() {
        return bike_property_id;
    }

    public void setBike_property_id(int bike_property_id) {
        this.bike_property_id = bike_property_id;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public String getBike_size() {
        return bike_size;
    }

    public void setBike_size(String bike_size) {
        this.bike_size = bike_size;
    }

    public String getBike_wheel_size() {
        return bike_wheel_size;
    }

    public void setBike_wheel_size(String bike_wheel_size) {
        this.bike_wheel_size = bike_wheel_size;
    }

    public String getBike_color() {
        return bike_color;
    }

    public void setBike_color(String bike_color) {
        this.bike_color = bike_color;
    }

    public String getBike_material() {
        return bike_material;
    }

    public void setBike_material(String bike_material) {
        this.bike_material = bike_material;
    }

    public String getBike_brake_type() {
        return bike_brake_type;
    }

    public void setBike_brake_type(String bike_brake_type) {
        this.bike_brake_type = bike_brake_type;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }
    
    
    

}
