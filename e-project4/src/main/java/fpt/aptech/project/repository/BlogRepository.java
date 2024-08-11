/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.BlogPost;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Admin
 */
public interface BlogRepository extends JpaRepository<BlogPost, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE BlogPost b SET b.active = :status WHERE b.id = :id")
    void updateActiveStatus(@Param("id") int id, @Param("status") boolean status);
}
