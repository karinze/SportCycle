/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.models;


import java.util.Date;

/**
 *
 * @author Manh_Chien
 */
public class UserDetails {

   
    public int userdetail_id;
    public Users users;
    public String first_name;
    public String last_name;
    public String email;
    public String phone_number;
    public String address;
    public String note;
    public Date created_dt;

    public UserDetails() {
    }

    public UserDetails(int userdetail_id, Users users, String first_name, String last_name, String email, String phone_number, String address, String note, Date created_dt) {
        this.userdetail_id = userdetail_id;
        this.users = users;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.note = note;
        this.created_dt = created_dt;
    }

    public UserDetails( Users users, String first_name, String last_name, String email, String phone_number, String address, String note, Date created_dt) {
        this.users = users;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.note = note;
        this.created_dt = created_dt;
    }
    
  

    public int getUserdetail_id() {
        return userdetail_id;
    }

    public void setUserdetail_id(int userdetail_id) {
        this.userdetail_id = userdetail_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }
    
    

}
