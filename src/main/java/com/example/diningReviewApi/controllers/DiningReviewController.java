package com.example.diningReviewApi.controllers;

import com.example.diningReviewApi.model.DiningReview;
import com.example.diningReviewApi.model.Restaurant;
import com.example.diningReviewApi.model.Status;
import com.example.diningReviewApi.repositories.DiningReviewRepository;
import com.example.diningReviewApi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rewiews")
public class DiningReviewController {
    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository userRepository;

    private final RestaurantContoller restaurantContoller;


    public DiningReviewController(DiningReviewRepository diningReviewRepository, UserRepository userRepository, RestaurantContoller restaurantContoller) {
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
        this.restaurantContoller = restaurantContoller;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitReview(@RequestBody DiningReview diningReview) {
        if (diningReview != null) {
            if (userRepository.existsByDisplayName(diningReview.getSubmittedBy())) {
                diningReview.setStatus(Status.PENDING);
                diningReviewRepository.save(diningReview);
                return ResponseEntity.ok(diningReview);
            } else {
                return ResponseEntity.badRequest().body("User does not exist");
            }
        }
        return ResponseEntity.noContent().build();
    }

    //As an admin, I want to get the list of all dining reviews
// that are pending approval.
    @GetMapping("admin/pending")
    public ResponseEntity<List<DiningReview>> getPendingReviews() {
        List<DiningReview> pendingReviews = diningReviewRepository.findByStatus(Status.PENDING);
        return ResponseEntity.ok(pendingReviews);
    }

    // Endpoint to approve a review
    @PutMapping("admin/approve/{reviewId}")
    public ResponseEntity<?> approveReview(@PathVariable Long reviewId) {
        Optional<DiningReview> reviewOptional = diningReviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            DiningReview review = reviewOptional.get();
            review.setStatus(Status.ACCEPTED);
            List<DiningReview> approvedReviewsByRestaurantId = diningReviewRepository.findByRestaurantIdAndStatus(review.getRestaurantId(), Status.ACCEPTED);
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
                double score;
                if (review.getEggScore() != null) {

                    List<DiningReview> eggReview = diningReviewRepository.findByRestaurantIdAndStatusAndEggScoreNotNull(review.getRestaurantId(), Status.ACCEPTED);
                    score = restaurant.getEggScore() + review.getEggScore();
                    double averageEggScore = score / (eggReview.size() + 1);
                    restaurant.setEggScore(averageEggScore);
                    return ResponseEntity.ok(review);
                }

                if (review.getDairyScore() != null) {
                    List<DiningReview> dairyReview = diningReviewRepository.findByRestaurantIdAndStatusAndDairyScoreNotNull(review.getRestaurantId(), Status.ACCEPTED);
                    score = restaurant.getDairyScore() + review.getDairyScore();
                    double averageDairyScore = score / (dairyReview.size() + 1);
                    restaurant.setDairyScore(averageDairyScore);
                    return ResponseEntity.ok(review);
                }

                if (review.getPeanutScore() != null) {
                    List<DiningReview> peanutReview = diningReviewRepository.findByRestaurantIdAndStatusAndPeanutScoreNotNull(review.getRestaurantId(), Status.ACCEPTED);
                    score = restaurant.getPeanutScore() + review.getPeanutScore();
                    double averagePeanutScore = score / (peanutReview.size() + 1);
                    restaurant.setPeanutScore(averagePeanutScore);
                    return ResponseEntity.ok(review);
                }
            }

        } else {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

    // Endpoint to reject a review
    @PutMapping("admin/reject/{reviewId}")
    public ResponseEntity<?> rejectReview(@PathVariable Long reviewId) {
        Optional<DiningReview> reviewOptional = diningReviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            DiningReview review = reviewOptional.get();
            review.setStatus(Status.REJECTED);
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
