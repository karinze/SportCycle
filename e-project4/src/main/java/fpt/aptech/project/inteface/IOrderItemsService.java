/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.entities.Orders;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IOrderItemsService {
    public List<OrderItems> findAll();

    public OrderItems createOrderItems(OrderItems orderItems);

    public OrderItems findOne(int orderItemsId);

    public void updateOrderItems(OrderItems orderItems);

    public void deleteOrderItems(int orderItemsId);
    
    public List<OrderItems> findbyorderitemsid(Orders orders);
    
    public List<OrderItems> findbyitemitemsid(int item);
}
