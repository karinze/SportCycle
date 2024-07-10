/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Users;

/**
 *
 * @author Manh_Chien
 */
public interface IAuthService {
    public Users checklogin(String username , String password);
}
