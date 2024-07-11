/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.dto;

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

public class OrderItemsDTO {
    public int order_item_id;
    public OrdersDTO orders;
    public ItemsDTO item;
    public int quantity;
    public BigDecimal price;
    public Date created_dt;
    public OrderItemsDTO() {
    }

    public OrderItemsDTO(int order_item_id) {
        this.order_item_id = order_item_id;
        
    }

    public OrderItemsDTO(int order_item_id, OrdersDTO orders, ItemsDTO item, int quantity, BigDecimal price, Date created_dt) {
        this.order_item_id = order_item_id;
        this.orders = orders;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.created_dt = created_dt;
    }
    
    

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public OrdersDTO getOrders() {
        return orders;
    }

    public void setOrders(OrdersDTO orders) {
        this.orders = orders;
    }

    public ItemsDTO getItem() {
        return item;
    }

    public void setItem(ItemsDTO item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }
            

    
    
    
}
