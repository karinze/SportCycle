/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IOrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> list() {
        return service.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Orders post(@RequestBody Orders newOrder) {
        return service.createOrder(newOrder);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Orders get(@PathVariable("orderId") int orderId) {
        return service.findOne(orderId);
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> list(@PathVariable("userId") UUID userId) {
        Users user = new Users();
        user.setUser_id(userId);
        return service.findUser(user);
    }
    
    @GetMapping("/userpage/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> page(@PathVariable("userId") UUID userId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        int validPageNumber = (pageNumber != null) ? pageNumber : 0;
        int validPageSize = (pageSize != null) ? pageSize : 5;
        Users user = service.findUser(userId);
        return service.page(user, validPageNumber, validPageSize);
    }

    @PostMapping(value = "/sendbillmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void sendMail(
            @RequestPart("users") Users users,
            @RequestPart("orders") Orders orders,
            @RequestPart("orderItems") List<OrderItems> orderItems) {
        service.sendBillMail(users, orders, orderItems);
    }
}
