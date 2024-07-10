/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.inteface.IBikeService;
import fpt.aptech.project.entities.Items;
import fpt.aptech.project.repository.BikeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class BikeService implements IBikeService{
    @Autowired
    BikeRepository bikeRepository;

    @Override
    public List<Items> findAll() {
        return bikeRepository.findAll();
    }

    @Override
    public void createBike(Items bikes) {
        bikeRepository.save(bikes);
    }

    @Override
    public Items findOne(int bikeId) {
        return bikeRepository.findById(bikeId).get();
    }

    @Override
    public void updateBike(Items bikes) {
        bikeRepository.save(bikes);
    }

    @Override
    public void deleteBike(int bikeId) {
        bikeRepository.deleteById(bikeId);
    }
    
}
