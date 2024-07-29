/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.ExternalTokens;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IExternalTokensService;
import fpt.aptech.project.repository.BikeRentalsRepository;
import fpt.aptech.project.repository.ExternalTokensRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class ExternalTokensService implements IExternalTokensService{
    
    @Autowired
    ExternalTokensRepository externalTokensRepository;
    
    @Override
    public List<ExternalTokens> findAll() {
        return externalTokensRepository.findAll();
    }

    @Override
    public void createExternalTokens(ExternalTokens externalTokens) {
        externalTokensRepository.save(externalTokens);
    }

    @Override
    public ExternalTokens findOne(int externalTokensId) {
        return externalTokensRepository.findById(externalTokensId).get();
    }

    @Override
    public void updateExternalTokens(ExternalTokens externalTokens) {
        externalTokensRepository.save(externalTokens);
    }

    @Override
    public void deleteExternalTokens(int externalTokensId) {
        externalTokensRepository.deleteById(externalTokensId);
    }

    @Override
    public ExternalTokens findByUserId(Users users) {
        return externalTokensRepository.listExternalByUserId(users);
    }
}
