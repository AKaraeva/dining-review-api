package com.example.diningReviewApi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DINING_REVIEWS")
@Getter
@Setter
@NoArgsConstructor
public class DiningReview {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="APPROVED")
    private Boolean approved;

    @Column(name = "SUBMITTED_BY")
    private String submittedBy;

    @Column(name = "RESTAURANT_ID")
    private Long restaurantId;

    @Column(name = "PEANUT_SCORE")
    private Integer peanutScore;

    @Column(name = "EGG_SCORE")
    private Integer eggScore;

    @Column(name = "DAIRY_SCORE")
    private Integer dairyScore;

    @Column(name = "COMMENTARY")
    private String commentary;
}