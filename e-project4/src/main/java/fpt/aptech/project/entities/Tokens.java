/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.project.entities;

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
@Table(name = "Tokens")
@Entity
@Setter
@Getter
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int token_id;
    @Column(name = "token")
    private String token;
    @Column(name = "token_expiry_date")
    private LocalDateTime token_expiry_date;
    @Column(name = "is_active")
    private int is_active;
    @ManyToOne
    @JoinColumn(name="user_id")
    public Users users;

    public Tokens() {
    }

    public Tokens(int token_id, String token, LocalDateTime token_expiry_date, int is_active, Users users) {
        this.token_id = token_id;
        this.token = token;
        this.token_expiry_date = token_expiry_date;
        this.is_active = is_active;
        this.users = users;
    }

    
}
