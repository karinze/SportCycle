/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IUserService;
import java.util.List;
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
@RequestMapping("/api/users")
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
    public Users get(@PathVariable("userId") int userId) {
        return service.findOne(userId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody Users newUser) {
        service.registerUser(newUser);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody Users editUser) {
        service.updateUser(editUser);
    }

}
