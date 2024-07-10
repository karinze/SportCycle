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
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */
@Table(name = "Order_Items")
@Entity
@Setter
@Getter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    public int order_item_id;
    @ManyToOne
    @JoinColumn(name="order_id")
    public Orders orders;
    @ManyToOne
    @JoinColumn(name="item_id")
    public Items item;
    @Column(name = "quantity")
    public int quantity;
    @Column(name = "price")
    public BigDecimal price;
    @Column(name = "created_dt")
    public Date created_dt;
    public OrderItems() {
    }

    public OrderItems(int order_item_id, Orders orders, Items item, int quantity, BigDecimal price, Date created_dt) {
        this.order_item_id = order_item_id;
        this.orders = orders;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.created_dt = created_dt;
    }
            

    
    
    
}
