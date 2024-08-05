/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Coupons;
import fpt.aptech.project.entities.Users;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface ICouponService {
    public List<Coupons> findAll();

    public Coupons createCoupons(Coupons coupons);

    public Coupons findOne(int couponsId);

    public void updateCoupons(Coupons coupons);

    public void deleteCoupons(int couponsId);
    
    public Coupons findCode(String code);
}
