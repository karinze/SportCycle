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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Coupon")
@Entity
@Setter
@Getter
public class Coupon {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private int code;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "coupon")
    private List<UserCoupon> userCoupons;
    
    public Coupon() {
    }

    public Coupon(int id, int code, Date creationDate, Date expirationDate, int quantity, double price) {
        this.id = id;
        this.code = code;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.price = price;
    }
}
