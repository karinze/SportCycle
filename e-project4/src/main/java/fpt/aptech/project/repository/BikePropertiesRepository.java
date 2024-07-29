/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.BikeProperties;
import fpt.aptech.project.entities.Items;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Manh_Chien
 */
public interface BikePropertiesRepository extends JpaRepository<BikeProperties, Integer> {
    @Query("SELECT o FROM BikeProperties o WHERE o.item = :item")
    BikeProperties listByItem(@Param("item") Items item);
}
