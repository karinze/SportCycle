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
 * @author Manh_Chien
 */
@Table(name = "Orders")
@Entity
@Setter
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    public int order_id;
    @ManyToOne
    @JoinColumn(name="user_id")
    public Users users;
    @Column(name = "total_amount")
    public BigDecimal total_amount;
    @Column(name = "order_date")
    public Date order_date;
    @Column(name = "status")
    public String status;
    @Column(name = "created_dt")
    public Date created_dt;
    
    @OneToMany(mappedBy = "orders")
    @JsonIgnore
    public List<OrderItems> list= new ArrayList();

    public Orders() {
    }

    public Orders(int order_id, Users users, BigDecimal total_amount, Date order_date, String status, Date created_dt) {
        this.order_id = order_id;
        this.users = users;
        this.total_amount = total_amount;
        this.order_date = order_date;
        this.status = status;
        this.created_dt = created_dt;
    }

    
    
    
}
