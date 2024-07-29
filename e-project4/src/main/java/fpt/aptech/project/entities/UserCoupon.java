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
 * @author Admin
 */
@Table(name = "User_Coupon")
@Entity
@Setter
@Getter
public class UserCoupon {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(name = "usage_date")
    private Date usageDate;

    public UserCoupon() {
    }

    public UserCoupon(Users user, Coupon coupon, Date usageDate) {
        this.user = user;
        this.coupon = coupon;
        this.usageDate = usageDate;
    }
}
