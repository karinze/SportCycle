/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.UserDetails;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IUserDetailService;
import fpt.aptech.project.repository.UserDetailRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class UserDetailService implements IUserDetailService{

    @Autowired
    UserDetailRepository userDetailRepository;
    
    
    @Override
    public List<UserDetails> findAll() {
        return userDetailRepository.findAll();
    }

    @Override
    public void createUserDetails(UserDetails userDetails) {
        userDetailRepository.save(userDetails);
    }

    @Override
    public UserDetails findOne(int userDetailsId) {
        return userDetailRepository.findById(userDetailsId).get();
    }

    @Override
    public void updateUserDetails(UserDetails userDetails) {
        userDetailRepository.save(userDetails);
    }

    @Override
    public void deleteUserDetails(int userDetailsId) {
        userDetailRepository.deleteById(userDetailsId);
    }

    @Override
    public List<UserDetails> findUserId(Users users) {
        List<UserDetails> user = userDetailRepository.findByUserId(users);
        if (user == null) {
            return new ArrayList<>(); // Return an empty list if no item found
        }
        return user;
    }
    
}
