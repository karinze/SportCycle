/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.CouponUsers;
import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.UserOrders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.ICouponUsersService;
import fpt.aptech.project.inteface.IOrderItemsService;
import fpt.aptech.project.inteface.IOrderService;
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
@RequestMapping("/api/couponusers/")
public class CouponUsersController {
    @Autowired
    ICouponUsersService service;
    
    @Autowired
    IUserService userService;
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<CouponUsers> list() {
        return service.findAll();
    }

    @GetMapping("/{couponUsersId}")
    @ResponseStatus(HttpStatus.OK)
    public CouponUsers get(@PathVariable("couponUsersId") int couponUsersId) {
        return service.findOne(couponUsersId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody CouponUsers newCouponUsers) {
        service.createCouponUsers(newCouponUsers);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody CouponUsers editCouponUsers) {
        service.updateCouponUsers(editCouponUsers);
    }

    @DeleteMapping("/{couponUsersId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int couponUsersId) {
        service.deleteCouponUsers(couponUsersId);
    }
    
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CouponUsers> list(@PathVariable("userId") UUID userId) {
        Users users = userService.findOne(userId); // Implement userService to fetch Users by ID
        return service.findByUserId(users);
    }
    
    @GetMapping("/coupon/{coupons}")
    @ResponseStatus(HttpStatus.OK)
    public List<CouponUsers> list(@PathVariable("coupons") Coupons coupons) {
        return service.findByCouponId(coupons);
    }
}
