/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
    
    @Query("SELECT o FROM Orders o WHERE o.users.id = :userId ORDER BY o.order_id DESC")
    List<Orders> listOrderByUserId(@Param("userId") UUID userId);
    
    @Query("SELECT o FROM Orders o WHERE o.users.id = :userId ORDER BY o.order_id DESC")
    Page<Orders> searchOrderByUserId(@Param("userId") UUID userId, Pageable pageable);
    
    @Query("SELECT SUM(o.total_amount) FROM Orders o WHERE MONTH(o.order_date) = MONTH(CURRENT_DATE) AND YEAR(o.order_date) = YEAR(CURRENT_DATE) AND o.status = 'Received'")
    BigDecimal getMonthlyRevenue();

    @Query("SELECT SUM(o.total_amount) FROM Orders o WHERE o.status = 'Received'")
    BigDecimal getTotalRevenue();

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = 'Pending'")
    Long getPendingRequests();
    
    @Query("SELECT new map(FUNCTION('MONTH', o.order_date) as month, SUM(o.total_amount) as total) " +
           "FROM Orders o WHERE o.status = 'Received' GROUP BY FUNCTION('MONTH', o.order_date)")
    List<Map<String, Object>> findMonthlyEarnings();
}
