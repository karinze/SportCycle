/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.ExternalTokens;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manh_Chien
 */
public interface ExternalTokensRepository extends JpaRepository<ExternalTokens, Integer> {
    
}
