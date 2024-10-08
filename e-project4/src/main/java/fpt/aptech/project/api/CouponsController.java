/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.ICouponService;
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
@RequestMapping("/api/coupons/")
public class CouponsController {
    @Autowired
    ICouponService service;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupons> list() {
        return service.findAll();
    }

    @GetMapping("/{couponsId}")
    @ResponseStatus(HttpStatus.OK)
    public Coupons get(@PathVariable("couponsId") int couponsId) {
        return service.findOne(couponsId);
    }
    
    @GetMapping("/findcode/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Coupons get(@PathVariable("code") String code) {
        return service.findCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coupons post(@RequestBody Coupons newCoupons) {
        return service.createCoupons(newCoupons);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody Coupons editCoupons) {
        service.updateCoupons(editCoupons);
    }

    @DeleteMapping("/{couponsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int couponsId) {
        service.deleteCoupons(couponsId);
    }
    
    
}
