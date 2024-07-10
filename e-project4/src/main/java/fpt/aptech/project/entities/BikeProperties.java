/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */
@Table(name = "BikeProperties")
@Entity
@Setter
@Getter
public class BikeProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_property_id")
    public int bike_property_id;
    
    @ManyToOne
    @JoinColumn(name="item_id")
    public Items item;
    
    @Column(name = "bike_size")
    public String bike_size;
    @Column(name = "bike_wheel_size")
    public String bike_wheel_size;
    @Column(name = "bike_color")
    public String bike_color;
    @Column(name = "bike_material")
    public String bike_material;
    @Column(name = "bike_brake_type")
    public String bike_brake_type;
    @Column(name = "created_dt")
    public Date created_dt;
    public BikeProperties() {
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

}
