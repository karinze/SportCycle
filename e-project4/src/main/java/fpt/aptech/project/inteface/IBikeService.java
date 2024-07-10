/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Items;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IBikeService {

    public List<Items> findAll();

    public void createBike(Items bikes);

    public Items findOne(int bikeId);

    public void updateBike(Items bikes);

    public void deleteBike(int bikeId);
}
