package fpt.aptech.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.*;

/**
 * UsersDTO class with validation annotations
 * 
 * @author Manh_Chien
 */
public class UsersDTO {
    private UUID user_id;

    @NotEmpty(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    public boolean role;


    public Date created_dt;

    public UsersDTO() {
    }

    public UsersDTO(UUID user_id) {
        this.user_id = user_id;
    }

    public UsersDTO(UUID user_id, String username, String password, String email, boolean role, Date created_dt) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.created_dt = created_dt;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }
}
