/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.project.entities;

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

@Table(name = "Coupons")
@Entity
@Setter
@Getter

public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    public int coupon_id;
    
    @Column(name = "code")
    public String code;
    
    @Column(name = "discount_amount")
    public BigDecimal discount_amount;
    
    @Column(name = "expiration_date")
    public Date expiration_date;
    
    @Column(name = "usage_limit")
    public int usage_limit;
    
    @Column(name = "is_active")
    public boolean is_active;
    
    @Column(name = "created_dt")
    public Date created_dt;
    
    @OneToMany(mappedBy = "coupons")
    @JsonIgnore
    public List<CouponUsers> clist= new ArrayList();

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

    
    
    
}
