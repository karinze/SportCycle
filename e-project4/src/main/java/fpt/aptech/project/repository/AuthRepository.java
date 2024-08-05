/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Manh_Chien
 */
public interface AuthRepository extends JpaRepository<Users, Integer> {
    @Query("Select u From Users u Where (u.email = :email OR u.username = :username) And u.password = :password")
    Users checkLogin(@Param("email") String email,@Param("username") String username,@Param("password") String password);
}
