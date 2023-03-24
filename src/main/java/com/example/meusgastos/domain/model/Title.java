package com.example.meusgastos.domain.model;

import java.util.Date;
import java.util.List;

// -----> import com.example.meusgastos.domain.Enum.ETipoTitulo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Title {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // temporario ----> private ETipoTitulo tipo;

    // @ManyToOne
    // @JoinColumn(name = "idCentroDeCusto")
    // private CentroDeCusto centroDeCusto;

    @ManyToMany
    @JoinTable(
        name = "title_costcenter",
        joinColumns = @JoinColumn(name = "title_id"),
        inverseJoinColumns = @JoinColumn(name = "costcenter_id")
    )
    private List<CostCenter> costCenters;

    @Column(nullable = false)
    private Double value;

    private Date created_at;

    private Date referenceDate;

    private Date dueDate;

    private Date payDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getpayDate() {
        return payDate;
    }

    public void setpayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    
}