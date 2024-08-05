/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.dto;

import fpt.aptech.client.models.*;
import java.util.Date;


/**
 *
 * @author Manh_Chien
 */

public class CouponUsersDTO {
    
    public int coupon_user_id;
    
    public Coupons coupons;
    
    public Users users;

    public Date created_dt;

    public CouponUsersDTO() {
    }

    public CouponUsersDTO(int coupon_user_id, Coupons coupons, Users users, Date created_dt) {
        this.coupon_user_id = coupon_user_id;
        this.coupons = coupons;
        this.users = users;
        this.created_dt = created_dt;
    }

    public int getCoupon_user_id() {
        return coupon_user_id;
    }

    public void setCoupon_user_id(int coupon_user_id) {
        this.coupon_user_id = coupon_user_id;
    }

    public Coupons getCoupons() {
        return coupons;
    }

    public void setCoupons(Coupons coupons) {
        this.coupons = coupons;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }
    
    
    
}
