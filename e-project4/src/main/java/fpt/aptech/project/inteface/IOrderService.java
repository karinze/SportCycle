/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Manh_Chien
 */
public interface IOrderService {
    public List<Orders> findAll();
    public Orders createOrder(Orders orders);
    public void sendBillMail(Users users, Orders order, List<OrderItems> orderItems);
    public Orders findOne(int orderId);
    public List<Orders> findUser(Users users);
}
