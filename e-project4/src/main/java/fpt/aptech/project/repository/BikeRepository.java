/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manh_Chien
 */
public interface BikeRepository extends JpaRepository<Items, Integer> {
    
}
