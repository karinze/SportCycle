/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Manh_Chien
 */
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT o FROM Orders o WHERE o.users.id = :userId")
    List<Orders> listOrderByUserId(@Param("userId") UUID userId);
    
    @Query("SELECT o FROM Orders o WHERE o.users.id = :userId")
    Page<Orders> searchOrderByUserId(@Param("userId") UUID userId, Pageable pageable);
}
