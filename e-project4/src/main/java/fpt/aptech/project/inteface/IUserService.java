/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Users;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IUserService {

    public List<Users> findAll();

    public void registerUser(Users user);

    public Users findOne(int userId);

    public void updateUser(Users users);

}
