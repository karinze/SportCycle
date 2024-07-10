/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */
@Table(name = "Admins")
@Entity
@Setter
@Getter
public class Admins {
    @Id
    @Column(name = "admin_id", columnDefinition = "UNIQUEIDENTIFIER")
    public UUID admin_id;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    public Users users;
    
    @Column(name = "permissions")
    public String permissions;

    public Admins() {
    }

    public Admins(UUID admin_id, Users users, String permissions) {
        this.admin_id = admin_id;
        this.users = users;
        this.permissions = permissions;
    }
    
}
