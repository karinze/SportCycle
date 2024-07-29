/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.*;
/**
 *
 * @author Manh_Chien
 */
@Table(name = "Users")
@Entity
@Setter
@Getter
public class Users {
    @Id
    
    @Column(name = "user_id", columnDefinition = "UNIQUEIDENTIFIER")
    public UUID user_id;
    @Column(name = "username")
    public String username;
    @Column(name = "password")
    public String password;
    @Column(name = "email")
    public String email;
    @Column(name = "role")
    public boolean role;
    @Column(name = "created_dt")
    public Date created_dt;
    
    @OneToMany(mappedBy = "users")
    @JsonIgnore
    public List<Admins> alist= new ArrayList();
    
    @OneToMany(mappedBy = "users")
    @JsonIgnore
    public List<Orders> olist= new ArrayList();
    
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserCoupon> userCoupons;
    
//    @OneToMany(mappedBy = "users")
//    @JsonIgnore
//    private List<Review> rlist= new ArrayList();

    public Users() {
    }

    public Users(UUID user_id, String username, String password, String email, boolean role, Date created_dt) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.created_dt = created_dt;
    }

    
 
    
}
