package com.example.diningReviewApi.repositories;

import com.example.diningReviewApi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>{
    Restaurant findByNameAndZipcode(String name, String zipcode);
    Restaurant findById(Long id);
}
