package com.example.myexpenses.domain.model;

import java.io.Serializable;
import java.util.Date;

import com.example.myexpenses.domain.enums.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

@Entity
public class Title implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "oneUserToManyTitles")
    @JoinColumn(name = "user_id")
    private User user;

    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "oneInvoiceToManyTitles")
    @JoinColumn(name = "invoice_id")
    private CreditCardInvoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "oneCostCenterToManyTitles")
    @JoinColumn(name="costcenter_id", nullable=false)
    private CostCenter costCenter;


    @Column(nullable = false)
    private Double value;

    private Date createdAt;

    private Date inativeAt;

    private Date referenceDate;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public CreditCardInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(CreditCardInvoice invoice) {
        this.invoice = invoice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getInativeAt() {
        return inativeAt;
    }

    public void setInativeAt(Date inativeAt) {
        this.inativeAt = inativeAt;
    }
}