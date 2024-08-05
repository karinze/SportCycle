/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


public class Coupons {
    
    public int coupon_id;
    
    public String code;
    
    public BigDecimal discount_amount;
    
    public Date expiration_date;
    
    public int usage_limit;
    
    public boolean is_active;
    
    public Date created_dt;

    public Coupons() {
    }

    public Coupons(int coupon_id, String code, BigDecimal discount_amount, Date expiration_date, int usage_limit, boolean is_active, Date created_dt) {
        this.coupon_id = coupon_id;
        this.code = code;
        this.discount_amount = discount_amount;
        this.expiration_date = expiration_date;
        this.usage_limit = usage_limit;
        this.is_active = is_active;
        this.created_dt = created_dt;
    }
    
    public Coupons( String code, BigDecimal discount_amount, Date expiration_date, int usage_limit, boolean is_active, Date created_dt) {
        this.code = code;
        this.discount_amount = discount_amount;
        this.expiration_date = expiration_date;
        this.usage_limit = usage_limit;
        this.is_active = is_active;
        this.created_dt = created_dt;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(BigDecimal discount_amount) {
        this.discount_amount = discount_amount;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getUsage_limit() {
        return usage_limit;
    }

    public void setUsage_limit(int usage_limit) {
        this.usage_limit = usage_limit;
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
