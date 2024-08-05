/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.CouponUsers;
import fpt.aptech.project.entities.UserDetails;
import fpt.aptech.project.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Manh_Chien
 */
public interface UserDetailRepository extends JpaRepository<UserDetails, Integer> {
    @Query("SELECT o FROM UserDetails o Where o.users = :users")
    List<UserDetails> findByUserId(@Param("users") Users users);
}
