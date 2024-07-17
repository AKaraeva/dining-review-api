package com.example.diningReviewApi.controllers;

import com.example.diningReviewApi.model.User;
import com.example.diningReviewApi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*@PostMapping
    public User addUser(User user) {

        if(user != null){
            User userToAddOptional = userRepository.findByDisplayName(user.getDisplayName());
            if (userToAddOptional != null){
                userRepository.save(user);
                return user;
            }
            else{
                System.out.println("Username already exists");
                return null;
            }
        }
        return null;
    }*/

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body("User data is missing");
        }
        User existingUser = userRepository.findByDisplayName(user.getDisplayName());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    @PutMapping("/{id}")
    public User updateUserProfile(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            //ignore update display name. It is unique. If user wants to change display name, they need to create a new account
           if(user.getCity() != null){
               userToUpdate.setCity(user.getCity());
           }
           if(user.getState() != null){
               userToUpdate.setState(user.getState());
              }
           if(user.getZipcode() != null){
               userToUpdate.setZipcode(user.getZipcode());
              }
           if (user.getInterestedInPeanut() != null){
               userToUpdate.setInterestedInPeanut(user.getInterestedInPeanut());
            }
           if(user.getInterestedInEgg() != null){
               userToUpdate.setInterestedInEgg(user.getInterestedInEgg());
              }
           if(user.getInterestedInDairy() != null){
               userToUpdate.setInterestedInDairy(user.getInterestedInDairy());
              }
            userRepository.save(userToUpdate);
            return userToUpdate;
        }
        return null;
    }

    @GetMapping("/{displayName}")
    public User getUserByDisplayName(@PathVariable String displayName) {
        return userRepository.findByDisplayName(displayName);
    }

    // In your review submission method
    public boolean validateUserForReview(String displayName) {
        // Check if user exists by display name
        return userRepository.existsByDisplayName(displayName);
    }
}

