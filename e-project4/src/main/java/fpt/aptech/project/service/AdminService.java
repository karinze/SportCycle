/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.inteface.IAdminRepository;
import fpt.aptech.project.entities.Admins;
import fpt.aptech.project.repository.AdminRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class AdminService implements IAdminRepository{
    @Autowired
    AdminRepository adminRepository;
    
    @Override
    public List<Admins> findAll() {
        return adminRepository.findAll();
    }
    
}
