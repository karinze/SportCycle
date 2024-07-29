/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Coupon;
import fpt.aptech.project.inteface.ICouponService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/coupon/")
public class CouponController {
        @Autowired
    private ICouponService service;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> list() {
        return service.findall();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody Coupon newCoupon) {
        service.createCoupon(newCoupon);
    }
}
