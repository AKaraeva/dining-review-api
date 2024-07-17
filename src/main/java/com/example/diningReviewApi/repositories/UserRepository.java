package com.example.diningReviewApi.repositories;

import com.example.diningReviewApi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByDisplayName(String username);

    boolean existsByDisplayName(String username);
}
