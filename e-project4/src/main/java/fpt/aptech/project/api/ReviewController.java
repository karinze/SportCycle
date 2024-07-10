///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
// */
//package fpt.aptech.project.api;
//
//import fpt.aptech.project.entities.Orders;
//import fpt.aptech.project.entities.Review;
//import fpt.aptech.project.inteface.IOrderService;
//import fpt.aptech.project.inteface.IReviewService;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
///**
// *
// * @author Manh_Chien
// */
//@RestController
//@RequestMapping("/api/review")
//public class ReviewController {
//    @Autowired
//    IReviewService service;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void post(@RequestBody Review newReview) {
//        service.createReview(newReview);
//    }
//
//    @DeleteMapping("/{reviewId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable int reviewId) {
//        service.deleteReview(reviewId);
//    }
//
//    @GetMapping("/bikes/{bikeId}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Review> list(@PathVariable("bikeId") int bikeId) {
//        return service.showReview(bikeId);
//    }
//}
