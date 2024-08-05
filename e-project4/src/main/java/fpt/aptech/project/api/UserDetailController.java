/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.CouponUsers;
import fpt.aptech.project.entities.UserDetails;
import fpt.aptech.project.entities.UserOrders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IUserDetailService;
import fpt.aptech.project.inteface.IUserOrdersService;
import fpt.aptech.project.inteface.IUserService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/userdetail/")
public class UserDetailController {
    @Autowired
    IUserDetailService service;
    @Autowired
    IUserService userService;
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDetails> list() {
        return service.findAll();
    }

    @GetMapping("/{userDetailsId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails get(@PathVariable("userDetailsId") int userDetailsId) {
        return service.findOne(userDetailsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody UserDetails newUserDetails) {
        service.createUserDetails(newUserDetails);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody UserDetails editUserDetails) {
        service.updateUserDetails(editUserDetails);
    }

    @DeleteMapping("/{userDetailsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int userDetailsId) {
        service.deleteUserDetails(userDetailsId);
    }
    
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDetails> list(@PathVariable("userId") UUID userId) {
        Users users = userService.findOne(userId); // Implement userService to fetch Users by ID
        return service.findUserId(users);
    }
}
