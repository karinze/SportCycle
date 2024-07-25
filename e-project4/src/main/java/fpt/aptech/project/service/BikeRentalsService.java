/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IBikeRentalsService;
import fpt.aptech.project.repository.BikeRentalsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public BikeRentals createBikeRentals(BikeRentals bikeRentals) {
        return bikeRentalsRepository.save(bikeRentals);
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
    
    @Override
    public List<BikeRentals> findUsers(Users users) {
        return bikeRentalsRepository.listBikeRentalsByUserId(users.getUser_id());
    }

    @Override
    public List<BikeRentals> pages(Users users, int pageNumber, int pageSize) {
        try {
            int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
            Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
            Page<BikeRentals> pageItems = this.bikeRentalsRepository.pages(users.getUser_id(),pageable);
            return pageItems.getContent();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch paginated items: " + ex.getMessage());
        }
    }

    @Override
    public List<BikeRentals> findUser(Users users) {
        return bikeRentalsRepository.listBikeRentalByUserId(users.getUser_id());
    }

    @Override
    public List<BikeRentals> page(Users users, int pageNumber, int pageSize) {
        try {
            int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
            Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
            Page<BikeRentals> pageItems = this.bikeRentalsRepository.page(users.getUser_id(),pageable);
            return pageItems.getContent();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch paginated items: " + ex.getMessage());
        }
    }
    
}
