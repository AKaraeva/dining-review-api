package com.example.diningReviewApi.controllers;

import com.example.diningReviewApi.model.Restaurant;
import com.example.diningReviewApi.repositories.RestaurantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantContoller {
    private final RestaurantRepository restaurantRepository;

    public RestaurantContoller(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/add")
    public ResponseEntity <?> addRestaurant(@RequestBody Restaurant restaurant) {
        if (restaurant != null && restaurant.getName() != null && restaurant.getZipcode() != null) {
            /*for (Restaurant r : restaurantRepository.findAll()) {
                if (r.getName().equals(restaurant.getName())) {
                    for (Restaurant res : restaurantRepository.findAll()) {
                        if (res.getZipcode().equals(restaurant.getZipcode())) {
                            return ResponseEntity.badRequest().body("Restaurant already exists");
                        }
                    }
                }
            }
            restaurantRepository.save(restaurant);
            return ResponseEntity.ok(restaurant);*/
            Restaurant restaurantOptional = restaurantRepository.findByNameAndZipcode(restaurant.getName(), restaurant.getZipcode());
            if (restaurantOptional != null) {
                return ResponseEntity.badRequest().body("Restaurant already exists");
            }
            restaurantRepository.save(restaurant);
            return ResponseEntity.ok(restaurant);
        }
        return ResponseEntity.badRequest().body("Invalid restaurant");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            return ResponseEntity.ok(restaurantOptional.get());
        } else {

            return ResponseEntity.notFound().build();
        }
    }
}

