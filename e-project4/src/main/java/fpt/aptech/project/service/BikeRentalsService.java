/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.inteface.IBikeRentalsService;
import fpt.aptech.project.repository.BikeRentalsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class BikeRentalsService implements IBikeRentalsService{
    
    @Autowired
    BikeRentalsRepository bikeRentalsRepository;
    
    @Override
    public List<BikeRentals> findAll() {
        return bikeRentalsRepository.findAll();
    }

    @Override
    public void createBikeRentals(BikeRentals bikeRentals) {
        bikeRentalsRepository.save(bikeRentals);
    }

    @Override
    public BikeRentals findOne(int bikeRentalsId) {
        return bikeRentalsRepository.findById(bikeRentalsId).get();
    }

    @Override
    public void updateBikeRentals(BikeRentals bikeRentals) {
        bikeRentalsRepository.save(bikeRentals);
    }

    @Override
    public void deleteBikeRentals(int bikeRentalsId) {
        bikeRentalsRepository.deleteById(bikeRentalsId);
    }
    
}
