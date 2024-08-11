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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Table(name = "Items")
@Entity
@Setter
@Getter
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    public int item_id;
    @Column(name = "name")
    public String name;
    @Column(name = "brand")
    public String brand;
    @Column(name = "description")
    public String description;
    @Column(name = "price")
    public BigDecimal price;
    @Column(name = "stock")
    public int stock;
    @Column(name = "rentalquantity")
    public int rentalquantity;
    @Column(name = "type")
    public String type;
    @Column(name = "image")
    public String image;
    @Column(name = "is_visible")
    public boolean is_visible;
    @Column(name = "created_dt")
    public Date created_dt;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    public List<OrderItems> oIlist= new ArrayList();
    
    @OneToMany(mappedBy = "item")
    @JsonIgnore
    public List<BikeProperties> Blist= new ArrayList();
    
    
    
    public Items() {
    }

    public Items(int item_id, String name, String brand, String description, BigDecimal price, int stock, int rentalquantity, String type, String image, boolean is_visible, Date created_dt) {
        this.item_id = item_id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.rentalquantity = rentalquantity;
        this.type = type;
        this.image = image;
        this.is_visible = is_visible;
        this.created_dt = created_dt;
    }

    

    
    
    
}
