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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manh_Chien
 */
@Table(name = "ExternalTokens")
@Entity
@Setter
@Getter
public class ExternalTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "external_token_id")
    public int external_token_id;
    @Column(name = "refresh_token")
    public String refresh_token;
    @Column(name = "access_token")
    public String access_token;
    @Column(name = "token_expires")
    public Date token_expires;
    @Column(name = "created_dt")
    public Date created_dt;
    @OneToOne
    @JoinColumn(name="user_id")
    public Users users;
    public ExternalTokens() {
    }

    public ExternalTokens(int external_token_id, String refresh_token, String access_token, Date token_expires, Date created_dt, Users users) {
        this.external_token_id = external_token_id;
        this.refresh_token = refresh_token;
        this.access_token = access_token;
        this.token_expires = token_expires;
        this.created_dt = created_dt;
        this.users = users;
    }
    
}
