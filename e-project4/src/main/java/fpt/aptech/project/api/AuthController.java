/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IAuthService;
import fpt.aptech.project.inteface.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    IAuthService service;

    @GetMapping("/login/{email}/{username}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public Users login(@PathVariable("email") String email,@PathVariable("username") String username,@PathVariable("password") String password) {
        return service.checklogin(email,username, password);
    }
}
