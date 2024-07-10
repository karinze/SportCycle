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
@Table(name = "BikeRentals")
@Entity
@Setter
@Getter
public class BikeRentals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_rental_id")
    public int bike_rental_id;
    @ManyToOne
    @JoinColumn(name="item_id")
    public Items item;
    @ManyToOne
    @JoinColumn(name="user_id")
    public Users users;
    @Column(name = "rental_start_date")
    public Date rental_start_date;
    @Column(name = "rental_end_date")
    public Date rental_end_date;
    @Column(name = "is_active")
    public boolean is_active;
    @Column(name = "created_dt")
    public Date created_dt;

    public BikeRentals() {
    }

    public BikeRentals(int bike_rental_id, Items item, Users users, Date rental_start_date, Date rental_end_date, boolean is_active, Date created_dt) {
        this.bike_rental_id = bike_rental_id;
        this.item = item;
        this.users = users;
        this.rental_start_date = rental_start_date;
        this.rental_end_date = rental_end_date;
        this.is_active = is_active;
        this.created_dt = created_dt;
    }

    
    
    
    
}
