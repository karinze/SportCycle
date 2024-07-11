/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */

public class UserOrdersDTO {
    public int user_order_id;
    public UsersDTO users;
    public OrderItemsDTO orderItems;
    public Date created_dt;
    
    public UserOrdersDTO() {
    }

    public UserOrdersDTO(int user_order_id) {
        this.user_order_id = user_order_id;
        
    }

    public UserOrdersDTO(int user_order_id, UsersDTO users, OrderItemsDTO orderItems, Date created_dt) {
        this.user_order_id = user_order_id;
        this.users = users;
        this.orderItems = orderItems;
        this.created_dt = created_dt;
    }
    
    

    public int getUser_order_id() {
        return user_order_id;
    }

    public void setUser_order_id(int user_order_id) {
        this.user_order_id = user_order_id;
    }

    public UsersDTO getUsers() {
        return users;
    }

    public void setUsers(UsersDTO users) {
        this.users = users;
    }

    public OrderItemsDTO getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItemsDTO orderItems) {
        this.orderItems = orderItems;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }

    
    
}
