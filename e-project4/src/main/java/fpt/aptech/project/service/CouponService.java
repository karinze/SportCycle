/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.ICouponService;
import fpt.aptech.project.repository.CouponsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class CouponService implements ICouponService{

    @Autowired
    CouponsRepository couponsRepository;
    
    @Override
    public List<Coupons> findAll() {
        return couponsRepository.findAll();
    }

    @Override
    public Coupons createCoupons(Coupons coupons) {
        return couponsRepository.save(coupons);
    }

    @Override
    public Coupons findOne(int couponsId) {
        return couponsRepository.findById(couponsId).get();
    }

    @Override
    public void updateCoupons(Coupons coupons) {
        couponsRepository.save(coupons);
    }

    @Override
    public void deleteCoupons(int couponsId) {
        couponsRepository.deleteById(couponsId);
    }

    @Override
    public Coupons findCode(String code) {
        Coupons coupons = couponsRepository.findCode(code).orElse(null);
        return coupons;
    }

    
}
