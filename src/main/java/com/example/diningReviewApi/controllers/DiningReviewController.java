package com.example.diningReviewApi.controllers;

import com.example.diningReviewApi.model.DiningReview;
import com.example.diningReviewApi.model.Restaurant;
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

    private RestaurantContoller restaurantContoller;


    public DiningReviewController(DiningReviewRepository diningReviewRepository, UserRepository userRepository, RestaurantContoller restaurantContoller) {
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
        this.restaurantContoller = restaurantContoller;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitReview(@RequestBody DiningReview diningReview) {
        if (diningReview != null) {
            if (userRepository.existsByDisplayName(diningReview.getSubmittedBy())) {
                diningReview.setApproved(false);
                diningReviewRepository.save(diningReview);
                return ResponseEntity.ok(diningReview);
            } else {
                return ResponseEntity.badRequest().body("User does not exist");
            }
        }
        return ResponseEntity.badRequest().body("Invalid review");
    }

    //As an admin, I want to get the list of all dining reviews
// that are pending approval.
    @GetMapping("/pending")
    public ResponseEntity<List<DiningReview>> getPendingReviews() {
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
            List<DiningReview> approvedReviewsByRestaurantId = diningReviewRepository.findByRestaurantIdAndApprovedTrue(review.getRestaurantId());
            Restaurant restaurant = this.restaurantContoller.getRestaurantById(review.getRestaurantId());

            if (approvedReviewsByRestaurantId.isEmpty()) {

                if (review.getEggScore() != null) {
                    restaurant.setEggScore(review.getEggScore() * 1.00);
                    return ResponseEntity.ok(review);
                }
                if (review.getDairyScore() != null) {
                    restaurant.setDairyScore(review.getDairyScore() * 1.00);
                    return ResponseEntity.ok(review);
                }

                if (review.getPeanutScore() != null) {
                    restaurant.setPeanutScore(review.getPeanutScore() * 1.00);
                    return ResponseEntity.ok(review);
                }
            } else {
                double score = 0;
                if (review.getEggScore() != null) {

                    List<DiningReview> eggReview = diningReviewRepository.findByRestaurantIdAndApprovedTrueAndEggScoreNotNull(review.getRestaurantId());
                    score = restaurant.getEggScore() + review.getEggScore();
                    double averageEggScore = score / (eggReview.size() + 1);
                    restaurant.setEggScore(averageEggScore);
                    diningReviewRepository.save(review);
                    return ResponseEntity.ok(review);
                }

                if (review.getDairyScore() != null) {
                    List<DiningReview> dairyReview = diningReviewRepository.findByRestaurantIdAndApprovedTrueAndDairyScoreNotNull(review.getRestaurantId());
                    score = restaurant.getDairyScore() + review.getDairyScore();
                    double averageDairyScore = score / (dairyReview.size() + 1);
                    restaurant.setDairyScore(averageDairyScore);
                    diningReviewRepository.save(review);
                    return ResponseEntity.ok(review);
                }

                if (review.getPeanutScore() != null) {
                    List<DiningReview> peanutReview = diningReviewRepository.findByRestaurantIdAndApprovedTrueAndPeanutScoreNull(review.getRestaurantId());
                    score = restaurant.getPeanutScore() + review.getPeanutScore();
                    double averagePeanutScore = score / (peanutReview.size() + 1);
                    restaurant.setPeanutScore(averagePeanutScore);
                    diningReviewRepository.save(review);
                    return ResponseEntity.ok(review);
                }
            }

        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint to reject a review
    @PutMapping("/reject/{reviewId}")
    public ResponseEntity<?> rejectReview(@PathVariable Long reviewId) {
        Optional<DiningReview> reviewOptional = diningReviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            DiningReview review = reviewOptional.get();
            //review.setApproved(false);
            diningReviewRepository.delete(review);
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
