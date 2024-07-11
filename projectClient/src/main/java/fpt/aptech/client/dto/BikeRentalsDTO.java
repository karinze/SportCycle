/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.dto;

import java.util.Date;



/**
 *
 * @author Manh_Chien
 */

public class BikeRentalsDTO {
    
    public int bike_rental_id;
    public ItemsDTO item;
    public UsersDTO users;
    public Date rental_start_date;
    public Date rental_end_date;
    public boolean is_active;
    public Date created_dt;

    public BikeRentalsDTO() {
    }

    public BikeRentalsDTO(int bike_rental_id) {
        this.bike_rental_id = bike_rental_id;
    }

    public BikeRentalsDTO(int bike_rental_id, ItemsDTO item, UsersDTO users, Date rental_start_date, Date rental_end_date, boolean is_active, Date created_dt) {
        this.bike_rental_id = bike_rental_id;
        this.item = item;
        this.users = users;
        this.rental_start_date = rental_start_date;
        this.rental_end_date = rental_end_date;
        this.is_active = is_active;
        this.created_dt = created_dt;
    }
    
    

    public int getBike_rental_id() {
        return bike_rental_id;
    }

    public void setBike_rental_id(int bike_rental_id) {
        this.bike_rental_id = bike_rental_id;
    }

    public ItemsDTO getItem() {
        return item;
    }

    public void setItem(ItemsDTO item) {
        this.item = item;
    }

    public UsersDTO getUsers() {
        return users;
    }

    public void setUsers(UsersDTO users) {
        this.users = users;
    }

    public Date getRental_start_date() {
        return rental_start_date;
    }

    public void setRental_start_date(Date rental_start_date) {
        this.rental_start_date = rental_start_date;
    }

    public Date getRental_end_date() {
        return rental_end_date;
    }

    public void setRental_end_date(Date rental_end_date) {
        this.rental_end_date = rental_end_date;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }

    
    
    
    
}
