package com.example.myexpenses.domain.model;

import java.util.Date;
import java.util.List;

import com.example.myexpenses.domain.enums.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "costcenter")
public class CostCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "costcenter_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column
    private Type type;

    @Column(nullable = false)
    private boolean standard;

    @Column
    private Date inative_at;

    @ManyToOne
    @JsonBackReference(value = "oneUserToManyCostCenters")
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "costCenter")
    @JsonManagedReference(value = "oneCostCenterToManyTitles")
    private List<Title> titles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public Date getInative_at() {
        return inative_at;
    }

    public void setInative_at(Date inative_at) {
        this.inative_at = inative_at;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }
}