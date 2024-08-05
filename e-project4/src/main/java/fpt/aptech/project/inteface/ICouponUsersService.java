/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.CouponUsers;
import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.Users;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface ICouponUsersService {
    public List<CouponUsers> findAll();

    public void createCouponUsers(CouponUsers couponUsers);

    public CouponUsers findOne(int couponUsersId);

    public void updateCouponUsers(CouponUsers couponUsers);

    public void deleteCouponUsers(int couponUsersId);
    
    public List<CouponUsers> findByUserId(Users users);
    
    public List<CouponUsers> findByCouponId(Coupons coupons);
}
