/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.Tokens;
import fpt.aptech.project.inteface.IUserService;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.repository.TokensRepository;
import fpt.aptech.project.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author Manh_Chien
 */
@Service
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    TokensRepository tokensRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    final JavaMailSender javaMailSender ;

    public UserService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    
    
    

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void registerUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public Users findOne(UUID userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public void updateUser(Users users) {
        userRepository.save(users);
    }

    @Override
    public List<Users> searchUsers(String username) {
        return userRepository.searchByUsers(username);
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.resetPassword(email);
    }
    
    @Transactional
    public void sendMail(Users users) {
        try {
            String resetLink = generateResetToken(users);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("XYZ Team <chientestphp@gmail.com>");
            msg.setTo(users.getEmail());
            msg.setSubject("Welcome to XYZ");
            msg.setText("Dear User,\n\nPlease click the following link to reset your password:\n" + resetLink + "\n\nBest regards,\nXYZ Team");
            javaMailSender.send(msg);
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    @Transactional
    private String generateResetToken(Users users) {
        List<Tokens> list = tokensRepository.FindByUsername(users);
        for (Tokens token : list) {
            if(token.getUsers().getUser_id().equals(users.getUser_id())){
                token.setIs_active(1);
            }
            Tokens pass = new Tokens(token.getToken_id(), token.getToken(), token.getToken_expiry_date(), token.getIs_active(),users);
            tokensRepository.save(pass);
        }
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
        Tokens resetToken = new Tokens();
        
        resetToken.setToken(uuid.toString());
        resetToken.setToken_expiry_date(expiryDateTime);
        resetToken.setIs_active(0);
        resetToken.setUsers(users);
        
        Tokens token = entityManager.merge(resetToken);
        if (token != null) {
            String link = "http://localhost:8888/resetPassword";
            return link + "/" + resetToken.getToken();
        } else {
            return "";
        }
    }

    @Override
    public boolean hashExipred(LocalDateTime eDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return eDateTime.isAfter(currentDateTime);
    }
    
    @Override
    public void savePass(Tokens newResetToken) {
        tokensRepository.save(newResetToken);
    }

    @Override
    public Tokens findToken(String token) {
        return tokensRepository.FindByToken(token);
    }


    @Override
    public void saveTokenPass(List<Tokens> list) {
        tokensRepository.saveAll(list);
    }

    @Override
    public List<Tokens> getResetTokens(Users users) {
        return tokensRepository.FindByUsername(users);
    }

}
