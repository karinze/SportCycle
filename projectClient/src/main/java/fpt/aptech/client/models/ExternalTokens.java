/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.client.models;

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

public class ExternalTokens {
    public int external_token_id;
    public String refresh_token;
    public String access_token;
    public Date token_expires;
    public Date created_dt;
    public Users users;
    public ExternalTokens() {
    }

    public ExternalTokens(int external_token_id) {
        this.external_token_id = external_token_id;
        
    }

    public ExternalTokens(int external_token_id, String refresh_token, String access_token, Date token_expires, Date created_dt, Users users) {
        this.external_token_id = external_token_id;
        this.refresh_token = refresh_token;
        this.access_token = access_token;
        this.token_expires = token_expires;
        this.created_dt = created_dt;
        this.users = users;
    }
    

    public int getExternal_token_id() {
        return external_token_id;
    }

    public void setExternal_token_id(int external_token_id) {
        this.external_token_id = external_token_id;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getToken_expires() {
        return token_expires;
    }

    public void setToken_expires(Date token_expires) {
        this.token_expires = token_expires;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    
}
