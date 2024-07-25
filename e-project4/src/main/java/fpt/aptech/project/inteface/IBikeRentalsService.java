/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.BikeProperties;
import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.Users;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author Manh_Chien
 */
public interface IBikeRentalsService {
    public List<BikeRentals> findAll();

    public BikeRentals createBikeRentals(BikeRentals bikeRentals);

    public BikeRentals findOne(int bikeRentalsId);

    public void updateBikeRentals(BikeRentals bikeRentals);

    public void deleteBikeRentals(int bikeRentalsId);
    
    public List<BikeRentals> findUsers(Users users);
    
    public List<BikeRentals> pages(Users users,int pageNumber,int pageSize);
    
    public List<BikeRentals> findUser(Users users);
    
    public List<BikeRentals> page(Users users,int pageNumber,int pageSize);
    
    
}
