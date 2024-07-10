/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Manh_Chien
 */
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT o FROM Orders t Where t.users=:users")
    List<Orders> listOrderByUserId(@PathVariable("users") int users);
}
