///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
// */
//package fpt.aptech.project.service;
//
//import fpt.aptech.project.entities.Items;
//import fpt.aptech.project.entities.OrderItems;
//import fpt.aptech.project.entities.Orders;
//import fpt.aptech.project.entities.RentalItems;
//import fpt.aptech.project.inteface.IOrderItemsService;
//import fpt.aptech.project.inteface.IRentalItemsService;
//import fpt.aptech.project.repository.ItemRepository;
//import fpt.aptech.project.repository.OrderItemsRepository;
//import fpt.aptech.project.repository.RentalItemsRepository;
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author Manh_Chien
// */
//@Service
//public class RentalItemsService implements IRentalItemsService{
//    @Autowired
//    RentalItemsRepository rentalItemsRepository;
//    
//    @Autowired
//    ItemRepository itemRepository;
//
//    @Override
//    public List<RentalItems> findAll() {
//        return rentalItemsRepository.findAll();
//    }
//
//    @Override
//    public RentalItems createRentalItems(RentalItems rentalItems) {
//        return rentalItemsRepository.save(rentalItems);
//    }
//
//    @Override
//    public RentalItems findOne(int rentalItemsId) {
//        return rentalItemsRepository.findById(rentalItemsId).get();
//    }
//
//    @Override
//    public void updateRentalItems(RentalItems rentalItems) {
//        rentalItemsRepository.save(rentalItems);
//    }
//
//    @Override
//    public void deleteRentalItems(int rentalItemsId) {
//        rentalItemsRepository.deleteById(rentalItemsId);
//    }
//
//    @Override
//    public List<RentalItems> findbyitemitemsid(int itemid) {
//        Items item = itemRepository.findById(itemid).orElse(null);
//        if (item == null) {
//            return new ArrayList<>(); // Return an empty list if no item found
//        }
//        return rentalItemsRepository.searchByItemsid(item);
//    }
//
//    
//    
//    
//}
