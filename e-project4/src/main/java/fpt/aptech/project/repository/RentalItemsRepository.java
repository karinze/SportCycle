///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
// */
//package fpt.aptech.project.repository;
//
//import fpt.aptech.project.entities.Items;
//import fpt.aptech.project.entities.OrderItems;
//import fpt.aptech.project.entities.Orders;
//import fpt.aptech.project.entities.RentalItems;
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.web.bind.annotation.PathVariable;
//
///**
// *
// * @author Manh_Chien
// */
//public interface RentalItemsRepository extends JpaRepository<RentalItems, Integer> {
//    
//    @Query("SELECT e FROM RentalItems e WHERE e.item = :item")
//    List<RentalItems> searchByItemsid(@Param("item") Items item);
//
//}
