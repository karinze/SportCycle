/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */

public class Tokens {
    private int token_id;
    private String token;
    private LocalDateTime token_expiry_date;
    private int is_active;
    public Users users;

    public Tokens() {
    }

    public Tokens(int token_id) {
        this.token_id = token_id;
        
    }

    public Tokens(int token_id, String token, LocalDateTime token_expiry_date, int is_active, Users users) {
        this.token_id = token_id;
        this.token = token;
        this.token_expiry_date = token_expiry_date;
        this.is_active = is_active;
        this.users = users;
    }
    

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getToken_expiry_date() {
        return token_expiry_date;
    }

    public void setToken_expiry_date(LocalDateTime token_expiry_date) {
        this.token_expiry_date = token_expiry_date;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    
}
