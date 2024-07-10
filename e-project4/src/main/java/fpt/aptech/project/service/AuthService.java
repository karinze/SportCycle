/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.inteface.IAuthService;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class AuthService implements IAuthService{
    @Autowired
    AuthRepository authRepository;
    
    @Override
    public Users checklogin(String username, String password) {
        return authRepository.checkLogin(username, password);
    }
    
}
