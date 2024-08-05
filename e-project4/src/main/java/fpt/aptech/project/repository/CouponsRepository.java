/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.Users;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Manh_Chien
 */
public interface CouponsRepository extends JpaRepository<Coupons, Integer> {
    @Query("Select e From Coupons e Where e.code = :code")
    Optional<Coupons> findCode(@Param("code") String code);
}
