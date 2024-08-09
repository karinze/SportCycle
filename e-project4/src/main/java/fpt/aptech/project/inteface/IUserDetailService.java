/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.UserDetails;
import fpt.aptech.project.entities.Users;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IUserDetailService {
    public List<UserDetails> findAll();

    public UserDetails createUserDetails(UserDetails userDetails);

    public UserDetails findOne(int userDetailsId);

    public void updateUserDetails(UserDetails userDetails);

    public void deleteUserDetails(int userDetailsId);
    
    public List<UserDetails> findUserId(Users user);
}
