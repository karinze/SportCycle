///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package fpt.aptech.project.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import java.math.BigDecimal;
//import java.util.Date;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// *
// * @author Manh_Chien
// */
//@Table(name = "Rental_Items")
//@Entity
//@Setter
//@Getter
//public class RentalItems {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "rental_item_id")
//    public int rental_item_id;
//    @ManyToOne
//    @JoinColumn(name="item_id")
//    public Items item;
//    @Column(name = "stock")
//    public int stock;
//    @Column(name = "created_dt")
//    public Date created_dt;
//
//    public RentalItems() {
//    }
//
//    public RentalItems(int rental_item_id, Items item, int stock, Date created_dt) {
//        this.rental_item_id = rental_item_id;
//        this.item = item;
//        this.stock = stock;
//        this.created_dt = created_dt;
//    }
//    
//    
//}
