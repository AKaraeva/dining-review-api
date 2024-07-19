package com.example.diningReviewApi.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "RESTAURANTS")
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name ="CITY")
    private String city;

    @Column(name = "ZIPCODE")
    private String zipcode;

    @Column(name = "PHONE")
    private String phone;


    @Column(name = "PEANUT_SCORE", nullable = true)
    private Double peanutScore;

    @Column(name = "EGG_SCORE", nullable = true)
    private Double eggScore;

    @Column(name = "DAIRY_SCORE", nullable = true)
    private Double dairyScore;

    @Column(name = "OVERALL_SCORE", nullable = true)
    private Double overallScore;

    public void setEggScore(Double eggScore) {
        this.eggScore = eggScore;
    }

    public void setPeanutScore(Double peanutScore) {
        this.peanutScore = peanutScore;
    }

    public void setDairyScore(Double dairyScore) {
        this.dairyScore = dairyScore;
    }

    private void setOverallScore() {
        this.overallScore = (this.dairyScore + this.eggScore + this.peanutScore)/3;
    }
}
