/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.UserOrders;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IUserOrdersService {
    public List<UserOrders> findAll();

    public void createUserOrders(UserOrders userOrders);

    public UserOrders findOne(int userOrdersId);

    public void updateUserOrders(UserOrders userOrders);

    public void deleteUserOrders(int UserOrdersId);
}
