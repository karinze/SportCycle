///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
// */
//package fpt.aptech.project.service;
//
//import fpt.aptech.project.inteface.IReviewService;
//import fpt.aptech.project.entities.Items;
//import fpt.aptech.project.entities.Review;
//import fpt.aptech.project.repository.ReviewRepository;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author Manh_Chien
// */
//@Service
//public class ReviewService implements IReviewService{
//    @Autowired
//    ReviewRepository reviewRepository;
//    
//    @Override
//    public void createReview(Review review) {
//        reviewRepository.save(review);
//    }
//
//    
//
//    @Override
//    public void deleteReview(int reviewId) {
//        reviewRepository.deleteById(reviewId);
//    }
//
//    @Override
//    public List<Review> showReview(int bikeId) {
//        return reviewRepository.listReviewByBikeId(bikeId);
//    }
//    
//}
