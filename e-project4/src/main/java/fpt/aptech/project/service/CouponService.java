/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.Coupon;
import fpt.aptech.project.inteface.ICouponService;
import fpt.aptech.project.repository.CouponRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class CouponService implements ICouponService{
    @Autowired
    CouponRepository repository;
    @Override
    public List<Coupon> findall() {
        return repository.findAll();
    }

    @Override
    public void createCoupon(Coupon newCoupon) {
        repository.save(newCoupon);
    }
    
}
