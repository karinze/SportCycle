/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.inteface.IBikeRentalsService;
import fpt.aptech.project.inteface.IOrderItemsService;
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
@RequestMapping("/api/orderitems/")
public class OrderItemsController {
    @Autowired
    IOrderItemsService service;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItems> list() {
        return service.findAll();
    }

    @GetMapping("/{orderItemsId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItems get(@PathVariable("orderItemsId") int orderItemsId) {
        return service.findOne(orderItemsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItems post(@RequestBody OrderItems newOrderItems) {
        return service.createOrderItems(newOrderItems);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody OrderItems editOrderItems) {
        service.updateOrderItems(editOrderItems);
    }

    @DeleteMapping("/{orderItemsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int orderItemsId) {
        service.deleteOrderItems(orderItemsId);
    }
    
    @GetMapping("/order/{orders}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItems> list(Orders orders) {
        return service.findbyorderitemsid(orders);
    }
}
