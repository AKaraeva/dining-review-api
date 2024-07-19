package com.example.diningReviewApi.repositories;

import com.example.diningReviewApi.model.DiningReview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long>{
List<DiningReview> findByApprovedFalse();
List<DiningReview> findByRestaurantIdAndApprovedTrue(Long restaurantId);

List<DiningReview> findByRestaurantIdAndApprovedTrueAndEggScoreNotNull(Long restaurantId);

List<DiningReview> findByRestaurantIdAndApprovedTrueAndPeanutScoreNull(Long restaurantId);

List<DiningReview> findByRestaurantIdAndApprovedTrueAndDairyScoreNotNull(Long restaurantId);
}
