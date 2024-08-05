/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Manh_Chien
 */
public interface IOrderService {

    public List<Orders> findAll();

    public Orders createOrder(Orders orders);

    public Users findUser(UUID uuid);

    public void sendBillMail(Users users, Orders order, List<OrderItems> orderItems);

    public Orders findOne(int orderId);

    public List<Orders> findUser(Users users);

    public List<Orders> page(Users users, int pageNumber, int pageSize);

    public BigDecimal getMonthlyRevenue();
    
    public BigDecimal getTotalRevenue();
    
    public Long getPendingRequests();
    
    public List<Map<String, Object>> findMonthlyEarnings();
}
