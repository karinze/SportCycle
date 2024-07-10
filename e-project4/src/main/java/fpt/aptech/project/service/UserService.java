/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.inteface.IUserService;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void registerUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public Users findOne(int userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public void updateUser(Users users) {
        userRepository.save(users);
    }
    
}
