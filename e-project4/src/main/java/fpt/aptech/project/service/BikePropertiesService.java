/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.BikeProperties;
import fpt.aptech.project.entities.Items;
import fpt.aptech.project.inteface.IBikePropertiesService;
import fpt.aptech.project.repository.BikePropertiesRepository;
import fpt.aptech.project.repository.ItemRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class BikePropertiesService implements IBikePropertiesService{

    @Autowired
    BikePropertiesRepository bikePropertiesRepository;
    @Autowired
    private ItemRepository itemsRepository;
    
    @Override
    public List<BikeProperties> findAll() {
        return bikePropertiesRepository.findAll();
    }

    @Override
    public void createBikeProperties(BikeProperties bikeProperties) {
        bikePropertiesRepository.save(bikeProperties);
    }

    @Override
    public BikeProperties findOne(int bikePropertiesId) {
        return bikePropertiesRepository.findById(bikePropertiesId).get();
    }

    @Override
    public void updateBikeProperties(BikeProperties bikeProperties) {
        bikePropertiesRepository.save(bikeProperties);
    }

    @Override
    public void deleteBikeProperties(int bikePropertiesId) {
        bikePropertiesRepository.deleteById(bikePropertiesId);
    }

    @Override
    public BikeProperties findByItemId(int itemId) {
        Items item = itemsRepository.findById(itemId).orElse(null);
            return bikePropertiesRepository.listByItem(item);
    }
    
}
