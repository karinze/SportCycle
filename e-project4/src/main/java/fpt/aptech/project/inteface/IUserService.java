/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Tokens;
import fpt.aptech.project.entities.Users;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Manh_Chien
 */
public interface IUserService {

    public List<Users> findAll();

    public void registerUser(Users user);

    public Users findOne(UUID userId);

    public void updateUser(Users users);

    public List<Users> searchUsers(String username);

    public Users findByEmail(String email);

    public void sendMail(Users users);

    public boolean hashExipred(LocalDateTime eDateTime);
    
    public void savePass(Tokens newResetToken);
    
    public Tokens findToken(String token);
    
    public List<Tokens> getResetTokens(Users users);
    
    public void saveTokenPass(List<Tokens> list);

}
