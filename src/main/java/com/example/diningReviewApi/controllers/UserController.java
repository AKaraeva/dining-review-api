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

    @PostMapping("/add")
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
    public ResponseEntity<?> updateUserProfile(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            //ignore update display name. It is unique. If user wants to change display name, they need to create a new account
            if (user.getCity() != null) {
                userToUpdate.setCity(user.getCity());
            }
            if (user.getState() != null) {
                userToUpdate.setState(user.getState());
            }
            if (user.getZipcode() != null) {
                userToUpdate.setZipcode(user.getZipcode());
            }
            if (user.getInterestedInPeanut() != null) {
                userToUpdate.setInterestedInPeanut(user.getInterestedInPeanut());
            }
            if (user.getInterestedInEgg() != null) {
                userToUpdate.setInterestedInEgg(user.getInterestedInEgg());
            }
            if (user.getInterestedInDairy() != null) {
                userToUpdate.setInterestedInDairy(user.getInterestedInDairy());
            }
            userRepository.save(userToUpdate);
            return ResponseEntity.ok().body(userToUpdate);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{displayName}")
    public ResponseEntity<?> getUserByDisplayName(@PathVariable String displayName) {
        if (displayName != null) {
            User user = this.userRepository.findByDisplayName(displayName);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body("Display name is missing");
    }
}


