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

    @PostMapping("admin/add")
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
    public Restaurant getRestaurantById(@PathVariable Long id) {
        Restaurant restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional != null) {
            return restaurantOptional;
        } else {

            return null;
        }
    }

    @GetMapping("/{zipcode}/{allergyType}")
    public ResponseEntity<?> getRestaurantByZipcodeAndAllergyType(@PathVariable String zipcode, @PathVariable String allergyType) {
        if(zipcode == null || allergyType == null){
            return ResponseEntity.badRequest().body("Zipcode or allergy type is missing");
        }
        Optional<Restaurant> restaurantOptional = Optional.empty();
        switch (allergyType) {
            case "egg":
                restaurantOptional = restaurantRepository.findByZipcodeAndEggScoreNotNull(zipcode);
                break;
            case "peanut":
                restaurantOptional = restaurantRepository.findByZipcodeAndPeanutScoreNotNull(zipcode);
                break;
            case "dairy":
                restaurantOptional = restaurantRepository.findByZipcodeAndDairyScoreNotNull(zipcode);
                break;
        }
        if (restaurantOptional.isPresent()) {
            return ResponseEntity.ok(restaurantOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

