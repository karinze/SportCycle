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
//import java.util.Date;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// *
// * @author Manh_Chien
// */
//@Table(name = "Review")
//@Entity
//@Setter
//@Getter
//public class Review {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "review_id")
//    private int review_id;
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private Users users;
//    @JoinColumn(name="bike_id")
//    private Items bikes;
//    @Column(name = "rating")
//    private int rating;
//    @Column(name = "comment")
//    private String comment;
//    @Column(name = "created_at")
//    private Date created_at;
//
//    public Review() {
//    }
//
//    public Review(int review_id, Users users, Items bikes, int rating, String comment, Date created_at) {
//        this.review_id = review_id;
//        this.users = users;
//        this.bikes = bikes;
//        this.rating = rating;
//        this.comment = comment;
//        this.created_at = created_at;
//    }
//    
//    
//}
