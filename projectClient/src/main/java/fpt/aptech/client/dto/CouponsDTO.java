/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;



public class CouponsDTO {
    
    private int coupon_id;
    
    @NotNull(message = "Code cannot be null")
    @Size(min = 3, max = 10, message = "Code must be between 3 and 10 characters")
    private String code;
    
    @NotNull(message = "Discount amount cannot be null")
    private BigDecimal discount_amount;
    
    @NotNull(message = "Expiration date cannot be null")
    @Future(message = "Expiration date must be in the future")
    private Date expiration_date;
    
    @NotNull(message = "Usage limit cannot be null")
    @Min(value = 0, message = "Usage limit must be at least 0")
    private int usage_limit;
    
    private boolean is_active;
    
    private Date created_dt;

    public CouponsDTO() {
    }

    public CouponsDTO(int coupon_id, String code, BigDecimal discount_amount, Date expiration_date, int usage_limit, boolean is_active, Date created_dt) {
        this.coupon_id = coupon_id;
        this.code = code;
        this.discount_amount = discount_amount;
        this.expiration_date = expiration_date;
        this.usage_limit = usage_limit;
        this.is_active = is_active;
        this.created_dt = created_dt;
    }
    
    public CouponsDTO( String code, BigDecimal discount_amount, Date expiration_date, int usage_limit, boolean is_active, Date created_dt) {
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
