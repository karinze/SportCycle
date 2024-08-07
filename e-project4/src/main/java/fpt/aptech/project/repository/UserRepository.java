/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Users;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Manh_Chien
 */
public interface UserRepository extends JpaRepository<Users, UUID> {
    
    @Query("Select e From Users e Where e.username LIKE :username ")
    List<Users> searchByUsers(@Param("username") String username);
    
    @Query("Select e From Users e Where e.email = :email")
    Users resetPassword(@Param("email") String email);
    
    @Query("Select e From Users e Where e.username = :username")
    Users findUsername(@Param("username") String username);
}
