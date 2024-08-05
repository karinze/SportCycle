/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */
@Table(name = "UserDetails")
@Entity
@Setter
@Getter
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userdetail_id")
    public int userdetail_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    public Users users;
    @Column(name = "first_name")
    public String first_name;
    @Column(name = "last_name")
    public String last_name;
    @Column(name = "email")
    public String email;
    @Column(name = "phone_number")
    public String phone_number;
    @Column(name = "address")
    public String address;
    @Column(name = "note", columnDefinition = "TEXT")
    public String note;
    @Column(name = "created_dt")
    public Date created_dt;

    public UserDetails() {
    }

    public UserDetails(int userdetail_id, Users users, String first_name, String last_name, String email, String phone_number, String address, Date created_dt) {
        this.userdetail_id = userdetail_id;
        this.users = users;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.created_dt = created_dt;
    }

}
