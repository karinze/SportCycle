/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IOrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/orders/")
public class OrderController {

    @Autowired
    IOrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody Orders newOrder) {
        service.createOrder(newOrder);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Orders get(@PathVariable("orderId") int orderId) {
        return service.findOne(orderId);
    }

    @GetMapping("/users/{users}")
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> list(@PathVariable("users") Users users) {
        return service.findUser(users);
    }
}
