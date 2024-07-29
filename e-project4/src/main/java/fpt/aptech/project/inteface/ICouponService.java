/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Coupon;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ICouponService {
    public List<Coupon> findall();
    public void createCoupon(Coupon newCoupon);
}
