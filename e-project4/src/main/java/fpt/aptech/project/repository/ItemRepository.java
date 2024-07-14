/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Items;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Manh_Chien
 */
public interface ItemRepository extends JpaRepository<Items, Integer> {
    @Query("Select e From Items e Where e.name LIKE :name ")
    Page<Items> searchByNamePage(@Param("name") String name,Pageable pageable);
    
    @Query("Select e From Items e Where e.name LIKE :name ")
    List<Items> searchByName(@Param("name") String name);
}
