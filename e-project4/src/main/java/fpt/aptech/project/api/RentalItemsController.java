///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
// */
//package fpt.aptech.project.api;
//
//import fpt.aptech.project.entities.BikeRentals;
//import fpt.aptech.project.entities.Items;
//import fpt.aptech.project.entities.OrderItems;
//import fpt.aptech.project.entities.Orders;
//import fpt.aptech.project.entities.RentalItems;
//import fpt.aptech.project.inteface.IBikeRentalsService;
//import fpt.aptech.project.inteface.IOrderItemsService;
//import fpt.aptech.project.inteface.IRentalItemsService;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
///**
// *
// * @author Manh_Chien
// */
//@RestController
//@RequestMapping("/api/rentalitems/")
//public class RentalItemsController {
//    @Autowired
//    IRentalItemsService service;
//
//    @GetMapping("")
//    @ResponseStatus(HttpStatus.OK)
//    public List<RentalItems> list() {
//        return service.findAll();
//    }
//
//    @GetMapping("/{rentalItemsId}")
//    @ResponseStatus(HttpStatus.OK)
//    public RentalItems get(@PathVariable("rentalItemsId") int rentalItemsId) {
//        return service.findOne(rentalItemsId);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public OrderItems post(@RequestBody OrderItems newOrderItems) {
//        return service.createOrderItems(newOrderItems);
//    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void put(@RequestBody OrderItems editOrderItems) {
//        service.updateOrderItems(editOrderItems);
//    }
//
//    @DeleteMapping("/{orderItemsId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable int orderItemsId) {
//        service.deleteOrderItems(orderItemsId);
//    }
//    
//    @GetMapping("/order/{orders}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderItems> list(Orders orders) {
//        return service.findbyorderitemsid(orders);
//    }
//    
//    @GetMapping("/items/{itemId}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderItems> list(@PathVariable Integer itemId) {
//        return service.findbyitemitemsid(itemId);
//    }
//    
//    @GetMapping("/getTotalQuantitySold")
//    @ResponseStatus(HttpStatus.OK)
//    public Long getTotalQuantitySold() {
//        return service.getTotalQuantitySold();
//    }
//    
//}
