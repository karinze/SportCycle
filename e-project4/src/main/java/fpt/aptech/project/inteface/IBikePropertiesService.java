/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.BikeProperties;
import fpt.aptech.project.entities.Items;
import java.util.List;

/**
 *
 * @author Manh_Chien
 */
public interface IBikePropertiesService {
    public List<BikeProperties> findAll();

    public void createBikeProperties(BikeProperties bikeProperties);

    public BikeProperties findOne(int bikePropertiesId);

    public void updateBikeProperties(BikeProperties bikeProperties);

    public void deleteBikeProperties(int bikePropertiesId);
}
