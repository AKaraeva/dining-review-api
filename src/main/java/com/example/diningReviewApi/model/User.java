package com.example.diningReviewApi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "DISPLAY_NAME", unique = true)
    private String displayName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIPCODE")
    private String zipcode;

   //@Column (name ="EMAIL")
   // private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "INTERESTED_IN_PEANUT")
    private Boolean interestedInPeanut;

    @Column(name = "INTERESTED_IN_EGG")
    private Boolean interestedInEgg;

    @Column(name = "INTERESTED_IN_DAIRY")
    private Boolean interestedInDairy;

    /*@ManyToMany
    @JoinTable(
        name = "USER_RESTAURANTS",
        joinColumns = @JoinColumn(name = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "RESTAURANT_ID")
    )*/
}