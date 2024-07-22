/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.ExternalTokens;
import fpt.aptech.project.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Manh_Chien
 */
public interface ExternalTokensRepository extends JpaRepository<ExternalTokens, Integer> {
    @Query("SELECT o FROM ExternalTokens o Where o.users=:users")
    ExternalTokens listExternalByUserId(@PathVariable("users") Users users);
}
