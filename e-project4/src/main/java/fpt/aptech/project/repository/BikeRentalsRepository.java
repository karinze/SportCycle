/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.Orders;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Manh_Chien
 */

public interface BikeRentalsRepository extends JpaRepository<BikeRentals, Integer> {
    @Query("SELECT o FROM BikeRentals o WHERE o.users.id = :userId ORDER BY o.bike_rental_id DESC")
    List<BikeRentals> listBikeRentalsByUserId(@Param("userId") UUID userId);
    
    @Query("SELECT o FROM BikeRentals o WHERE o.users.id = :userId ORDER BY o.bike_rental_id DESC")
    Page<BikeRentals> pages(@Param("userId") UUID userId, Pageable pageable);
    
    @Query("SELECT o FROM BikeRentals o WHERE o.users.id = :userId AND o.is_active = true ORDER BY o.bike_rental_id DESC")
    List<BikeRentals> listBikeRentalByUserId(@Param("userId") UUID userId);
    
    @Query("SELECT o FROM BikeRentals o WHERE o.users.id = :userId AND o.is_active = true ORDER BY o.bike_rental_id DESC")
    Page<BikeRentals> page(@Param("userId") UUID userId, Pageable pageable);
}
