/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.CouponUsers;
import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.UserOrders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.ICouponUsersService;
import fpt.aptech.project.inteface.IUserOrdersService;
import fpt.aptech.project.repository.CouponUsersRepository;
import fpt.aptech.project.repository.UserOrdersRepository;
import fpt.aptech.project.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class CouponUsersService implements ICouponUsersService{

    @Autowired
    CouponUsersRepository couponUsersRepository;
    
    @Autowired
    UserRepository usersRepository;

    @Override
    public List<CouponUsers> findAll() {
        return couponUsersRepository.findAll();
    }

    @Override
    public void createCouponUsers(CouponUsers couponUsers) {
        couponUsersRepository.save(couponUsers);
    }

    @Override
    public CouponUsers findOne(int couponUsersId) {
        return couponUsersRepository.findById(couponUsersId).get();
    }

    @Override
    public void updateCouponUsers(CouponUsers couponUsers) {
        couponUsersRepository.save(couponUsers);
    }

    @Override
    public void deleteCouponUsers(int couponUsersId) {
        couponUsersRepository.deleteById(couponUsersId);
    }

    @Override
    public List<CouponUsers> findByUserId(Users users) {
        List<CouponUsers> user = couponUsersRepository.listCouponsByUserId(users);
        if (user == null) {
            return new ArrayList<>(); // Return an empty list if no item found
        }
        return user;
    }

    @Override
    public List<CouponUsers> findByCouponId(Coupons coupons) {
        return couponUsersRepository.listCouponsByCouponsId(coupons);
    }
    
    
    
}
