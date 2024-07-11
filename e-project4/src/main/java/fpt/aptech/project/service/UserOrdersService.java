/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.UserOrders;
import fpt.aptech.project.inteface.IUserOrdersService;
import fpt.aptech.project.repository.UserOrdersRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class UserOrdersService implements IUserOrdersService{

    @Autowired
    UserOrdersRepository userOrdersRepository;
    
    @Override
    public List<UserOrders> findAll() {
        return userOrdersRepository.findAll();
    }

    @Override
    public void createUserOrders(UserOrders userOrders) {
        userOrdersRepository.save(userOrders);
    }

    @Override
    public UserOrders findOne(int userOrdersId) {
        return userOrdersRepository.findById(userOrdersId).get();
    }

    @Override
    public void updateUserOrders(UserOrders userOrders) {
        userOrdersRepository.save(userOrders);
    }

    @Override
    public void deleteUserOrders(int UserOrdersId) {
        userOrdersRepository.deleteById(UserOrdersId);
    }
    
}
