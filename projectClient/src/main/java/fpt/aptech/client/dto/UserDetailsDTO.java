/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.dto;


import fpt.aptech.client.models.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 *
 * @author Manh_Chien
 */
public class UserDetailsDTO {

   
    public int userdetail_id;
    public Users users;
    @Size(max = 100, message = "First name cannot be longer than 100 characters")
    @NotEmpty(message = "First name is required")
    public String first_name;

    @Size(max = 100, message = "Last name cannot be longer than 100 characters")
    @NotEmpty(message = "Last name is required")
    public String last_name;

    @Size(max = 100, message = "Email name cannot be longer than 100 characters")
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    public String email;

    @Size(min = 10, max = 11, message = "Phone number must be between 10 and 11 digits")
    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^(?!0{10,11})(?!1{10,11})(?!2{10,11})(?!3{10,11})(?!4{10,11})(?!5{10,11})(?!6{10,11})(?!7{10,11})(?!8{10,11})(?!9{10,11})\\d{10,11}$", message = "Invalid phone number")
    public String phone_number;
    @Size(max = 100, message = "Address cannot be longer than 100 characters")
    @NotEmpty(message = "Address is required")
    public String address;
    public String note;
    public Date created_dt;

    public UserDetailsDTO() {
    }

    public UserDetailsDTO(int userdetail_id, Users users, String first_name, String last_name, String email, String phone_number, String address, Date created_dt) {
        this.userdetail_id = userdetail_id;
        this.users = users;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
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
