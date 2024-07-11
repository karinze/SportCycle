/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.UserOrders;
import fpt.aptech.project.inteface.IOrderItemsService;
import fpt.aptech.project.inteface.IOrderService;
import fpt.aptech.project.inteface.IUserOrdersService;
import java.util.List;
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
@RequestMapping("/api/userorders/")
public class UserOrdersController {
    @Autowired
    IUserOrdersService service;
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserOrders> list() {
        return service.findAll();
    }

    @GetMapping("/{userOrdersId}")
    @ResponseStatus(HttpStatus.OK)
    public UserOrders get(@PathVariable("userOrdersId") int userOrdersId) {
        return service.findOne(userOrdersId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody UserOrders newUserOrders) {
        service.createUserOrders(newUserOrders);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody UserOrders editUserOrders) {
        service.updateUserOrders(editUserOrders);
    }

    @DeleteMapping("/{userOrdersId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int userOrdersId) {
        service.deleteUserOrders(userOrdersId);
    }
}
