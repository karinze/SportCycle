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
@Table(name = "CouponUsers")
@Entity
@Setter
@Getter

public class CouponUsers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_user_id")
    public int coupon_user_id;
    
    @ManyToOne
    @JoinColumn(name="coupon_id")
    public Coupons coupons;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    public Users users;
    
    @Column(name = "created_dt")
    public Date created_dt;

    public CouponUsers() {
    }

    public CouponUsers(int coupon_user_id, Coupons coupons, Users users, Date created_dt) {
        this.coupon_user_id = coupon_user_id;
        this.coupons = coupons;
        this.users = users;
        this.created_dt = created_dt;
    }
    
    
    
}
