package com.example.diningReviewApi.repositories;

import com.example.diningReviewApi.model.Restaurant;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>{
    Restaurant findByNameAndZipcode(String name, String zipcode);
    Restaurant findById(Long id);

    @OrderBy("eggScore DESC")
    Optional <Restaurant> findByZipcodeAndEggScoreNotNull(String zipcode);
    @OrderBy("peanutScore DESC")
    Optional <Restaurant> findByZipcodeAndPeanutScoreNotNull(String zipcode);
    @OrderBy("dairyScore DESC")
    Optional <Restaurant> findByZipcodeAndDairyScoreNotNull(String zipcode);
}
