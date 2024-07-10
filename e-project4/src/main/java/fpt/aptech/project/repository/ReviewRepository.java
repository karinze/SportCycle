///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
// */
//package fpt.aptech.project.repository;
//
//import fpt.aptech.project.entities.Items;
//import fpt.aptech.project.entities.Review;
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.web.bind.annotation.PathVariable;
//
///**
// *
// * @author Manh_Chien
// */
//public interface ReviewRepository extends JpaRepository<Review, Integer> {
//    @Query("SELECT t FROM Review t Where t.bikes=:bikes")
//    List<Review> listReviewByBikeId(@PathVariable("bikes") int bikes);
//}
