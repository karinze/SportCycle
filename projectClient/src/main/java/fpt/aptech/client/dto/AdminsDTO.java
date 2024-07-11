/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.dto;


import java.util.UUID;


/**
 *
 * @author Manh_Chien
 */
public class AdminsDTO {
    
    public UUID admin_id;
    
    
    public UsersDTO users;
    
    
    public String permissions;

    public AdminsDTO() {
    }

    public AdminsDTO(UUID admin_id) {
        this.admin_id = admin_id;
    }

    public UUID getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(UUID admin_id) {
        this.admin_id = admin_id;
    }

    public UsersDTO getUsers() {
        return users;
    }

    public void setUsers(UsersDTO users) {
        this.users = users;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
    
}
