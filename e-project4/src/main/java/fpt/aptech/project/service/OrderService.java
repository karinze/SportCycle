/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.inteface.IOrderService;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class OrderService implements IOrderService{
    @Autowired
    OrderRepository orderRepository;
    
    @Override
    public void createOrder(Orders orders) {
        orderRepository.save(orders);
    }

    @Override
    public Orders findOne(int orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public List<Orders> findUser(int userId) {
        return orderRepository.listOrderByUserId(userId);
    }
    
}
