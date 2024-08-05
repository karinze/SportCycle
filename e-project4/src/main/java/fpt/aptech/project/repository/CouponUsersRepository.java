/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.CouponUsers;
import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.UserOrders;
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
public interface CouponUsersRepository extends JpaRepository<CouponUsers, Integer> {
    @Query("SELECT o FROM CouponUsers o Where o.users = :users")
    List<CouponUsers> listCouponsByUserId(@Param("users") Users users);
    
    @Query("SELECT o FROM CouponUsers o Where o.coupons = :coupons")
    List<CouponUsers> listCouponsByCouponsId(@Param("coupons") Coupons coupon);
}
