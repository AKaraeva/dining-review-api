package com.example.diningReviewApi.repositories;

import com.example.diningReviewApi.model.DiningReview;
import com.example.diningReviewApi.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
    List<DiningReview> findByStatus(Status status);
    List<DiningReview> findByRestaurantIdAndStatus(Long restaurantId, Status status);
    List<DiningReview> findByRestaurantIdAndStatusAndEggScoreNotNull(Long restaurantId, Status status);
    List<DiningReview> findByRestaurantIdAndStatusAndPeanutScoreNotNull(Long restaurantId, Status status);
    List<DiningReview> findByRestaurantIdAndStatusAndDairyScoreNotNull(Long restaurantId, Status status);
}