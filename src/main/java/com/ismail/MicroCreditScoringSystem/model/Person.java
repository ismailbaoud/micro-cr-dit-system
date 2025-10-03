package main.java.com.ismail.MicroCreditScoringSystem.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public abstract class Person {
    private String  firstName ;
    private String lastName;
    private LocalDate birthday;
    private String city;
    private boolean investment;
    private boolean placement  ;
    private String familyStatus;
    private Integer childrenCount;
    private LocalDateTime createdAt;
    private Integer score = 0;
    private UUID id ;



    public Person(String firstName, String lastName, LocalDate birthday, String city, boolean placement, boolean investment, String familyStatus,Integer childrenCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.city = city;
        this.placement = placement;
        this.investment = investment;
        this.familyStatus = familyStatus;
        this.createdAt = LocalDateTime.now();
        this.childrenCount = childrenCount;
        id = UUID.randomUUID();
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Integer childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean isInvestment() {
        return investment;
    }

    public void setInvestment(boolean investment) {
        this.investment = investment;
    }

    public boolean isPlacement() {
        return placement;
    }

    public void setPlacement(boolean placement) {
        this.placement = placement;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
