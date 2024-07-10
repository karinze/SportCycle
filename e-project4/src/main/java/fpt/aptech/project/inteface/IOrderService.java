/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IOrderService {
    public void createOrder(Orders orders);
    public Orders findOne(int orderId);
    public List<Orders> findUser(int userId);
}
