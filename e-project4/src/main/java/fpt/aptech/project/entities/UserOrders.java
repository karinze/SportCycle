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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */
@Table(name = "UserOrders")
@Entity
@Setter
@Getter
public class UserOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_order_id")
    public int user_order_id;
    @OneToOne
    @JoinColumn(name="user_id")
    public Users users;
    @OneToOne
    @JoinColumn(name="order_item_id")
    public OrderItems orderItems;
    @Column(name = "created_dt")
    public Date created_dt;
    
    public UserOrders() {
    }

    public UserOrders(int user_order_id, Users users, OrderItems orderItems, Date created_dt) {
        this.user_order_id = user_order_id;
        this.users = users;
        this.orderItems = orderItems;
        this.created_dt = created_dt;
    }

    
    
}
