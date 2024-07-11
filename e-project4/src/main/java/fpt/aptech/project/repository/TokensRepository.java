/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Tokens;
import fpt.aptech.project.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Manh_Chien
 */
public interface TokensRepository extends JpaRepository<Tokens, Integer> {
    @Query("Select e From Tokens e Where e.token = :token")
    Tokens FindByToken(@Param("token") String token);
    
    @Query("Select e From Tokens e Where e.users = :users ")
    List<Tokens> FindByUsername(@Param("users") Users users);
}
