package com.example.diningReviewApi.controllers;

import com.example.diningReviewApi.model.DiningReview;
import com.example.diningReviewApi.repositories.DiningReviewRepository;
import com.example.diningReviewApi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rewiews")
public class DiningReviewController {
    private DiningReviewRepository diningReviewRepository;
    private UserRepository userRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository, UserRepository userRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
    }

    @PostMapping ("/submit")
    public ResponseEntity<?> submitReview(@RequestBody DiningReview diningReview){
        if(diningReview != null){
            if(userRepository.existsByDisplayName(diningReview.getSubmittedBy())){
                diningReviewRepository.save(diningReview);
                return ResponseEntity.ok(diningReview);
            }
            else{
                return ResponseEntity.badRequest().body("User does not exist");
            }
        }
        return ResponseEntity.badRequest().body("Invalid review");
    }
//As an admin, I want to get the list of all dining reviews
// that are pending approval.
    @GetMapping("/pending")
    public ResponseEntity<List<DiningReview>> getPendingReviews(){
        List<DiningReview> pendingReviews = diningReviewRepository.findByApprovedFalse();
        return ResponseEntity.ok(pendingReviews);
    }

    // Endpoint to approve a review
    @PutMapping("/approve/{reviewId}")
    public ResponseEntity<?> approveReview(@PathVariable Long reviewId) {
        Optional<DiningReview> reviewOptional = diningReviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            DiningReview review = reviewOptional.get();
            review.setApproved(true);
            diningReviewRepository.save(review);
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to reject a review
    @PutMapping("/reject/{reviewId}")
    public ResponseEntity<?> rejectReview(@PathVariable Long reviewId) {
        Optional<DiningReview> reviewOptional = diningReviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            DiningReview review = reviewOptional.get();
            review.setApproved(false);
            diningReviewRepository.save(review);
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/approved/{restaurantId}")
    public ResponseEntity<List<DiningReview>> getApprovedReviewsForRestaurant(@PathVariable Long restaurantId) {
        List<DiningReview> approvedReviews = diningReviewRepository.findByRestaurantIdAndApprovedTrue(restaurantId);
        return ResponseEntity.ok(approvedReviews);
    }


}
