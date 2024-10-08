/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.inteface.IOrderItemsService;
import fpt.aptech.project.repository.ItemRepository;
import fpt.aptech.project.repository.OrderItemsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class OrderItemService implements IOrderItemsService{
    @Autowired
    OrderItemsRepository orderItemsRepository;
    
    @Autowired
    ItemRepository itemRepository;
    
    @Override
    public List<OrderItems> findAll() {
        return orderItemsRepository.findAll();
    }

    @Override
    public OrderItems createOrderItems(OrderItems orderItems) {
        return orderItemsRepository.save(orderItems);
    }

    @Override
    public OrderItems findOne(int orderItemsId) {
        return orderItemsRepository.findById(orderItemsId).get();
    }

    @Override
    public void updateOrderItems(OrderItems orderItems) {
        orderItemsRepository.save(orderItems);
    }

    @Override
    public void deleteOrderItems(int orderItemsId) {
        orderItemsRepository.deleteById(orderItemsId);
    }

    @Override
    public List<OrderItems> findbyorderitemsid(Orders orders) {
        return orderItemsRepository.searchByOrderItemsid(orders);
    }

    @Override
    public List<OrderItems> findbyitemitemsid(int itemId) {
        Items item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            return new ArrayList<>(); // Return an empty list if no item found
        }
        return orderItemsRepository.searchByItemsid(item);
    }

    @Override
    public Long getTotalQuantitySold() {
        return orderItemsRepository.getTotalQuantitySold();
    }
    
}
