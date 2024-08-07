/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Tokens;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IUserService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/users/")
public class UserController {

    @Autowired
    IUserService service;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Users> list() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Users get(@PathVariable("userId") UUID userId) {
        return service.findOne(userId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody Users newUser) {
        service.registerUser(newUser);
    }
    
    @GetMapping("/findemail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Users findByEmail(@PathVariable("email") String email) {
        return service.findByEmail(email);
    }
    
    @PostMapping("/sendmail")
    @ResponseStatus(HttpStatus.OK)
    public void sendmail(@RequestBody Users users) {
        service.sendMail(users);
    }
    
    @GetMapping("/findtoken/{token}")
    @ResponseStatus(HttpStatus.OK)
    public Tokens findToken(@PathVariable("token") String token) {
        return service.findToken(token);
    }
    
    @PostMapping("/resetpassword")
    @ResponseStatus(HttpStatus.OK)
    public void resetpassword(@RequestBody Users resetpass) {
        service.updateUser(resetpass);
    }
    
    @PostMapping("/savetoken")
    @ResponseStatus(HttpStatus.OK)
    public void savetoken(@RequestBody Tokens resettoken) {
        service.savePass(resettoken);
    }
    
    @GetMapping("/finduser/{users}")
    @ResponseStatus(HttpStatus.OK)
    public List<Tokens> findToken(@PathVariable("users") Users users) {
        return service.getResetTokens(users);
    }
    
    @PutMapping("/savetokenactive")
    @ResponseStatus(HttpStatus.OK)
    public void savetokenactive(@RequestBody List<Tokens> resettoken) {
        service.saveTokenPass(resettoken);
    }
    
    @GetMapping("/findusername/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Users findUsername(@PathVariable("username") String username) {
        return service.findUsername(username);
    }
}
