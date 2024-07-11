/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.BikeProperties;
import fpt.aptech.project.entities.BikeRentals;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IBikeRentalsService {
    public List<BikeRentals> findAll();

    public void createBikeRentals(BikeRentals bikeRentals);

    public BikeRentals findOne(int bikeRentalsId);

    public void updateBikeRentals(BikeRentals bikeRentals);

    public void deleteBikeRentals(int bikeRentalsId);
}
