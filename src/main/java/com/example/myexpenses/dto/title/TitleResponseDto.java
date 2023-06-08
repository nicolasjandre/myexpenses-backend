package com.example.myexpenses.dto.title;

import java.util.Date;

import com.example.myexpenses.domain.enums.Type;
import com.example.myexpenses.dto.costcenter.CostCenterResponseDto;
import com.example.myexpenses.dto.creditcard.CreditCardResponseDto;
import com.example.myexpenses.dto.creditcardinvoice.CreditCardInvoiceResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class TitleResponseDto {

    private Long id;

    private String description;

    private Type type;

    @JsonIgnoreProperties({ "standard", "type", "inative_at" })
    private CostCenterResponseDto costCenter;

    @JsonIgnoreProperties({ "creditLimit", "availableLimit", "flag", "bank", "closingDay", "dueDay", "inativeAt" })
    private CreditCardResponseDto creditCard;

    @JsonIgnoreProperties({ "creditCard", "titles", "closingDate" })
    private CreditCardInvoiceResponseDto invoice;

    private Double value;

    private Date referenceDate;

    private Date createdAt;

    private Date inativeAt;

    private String notes;

    public TitleResponseDto() {
    }

    public TitleResponseDto(Long id, String description, Type type, CostCenterResponseDto costCenter,
            CreditCardResponseDto creditCard, CreditCardInvoiceResponseDto invoice, Double value, Date referenceDate,
            Date createdAt, Date inativeAt, String notes) {

        this.id = id;
        this.description = description;
        this.type = type;
        this.costCenter = costCenter;
        this.creditCard = creditCard;
        this.invoice = invoice;
        this.value = value;
        this.referenceDate = referenceDate;
        this.createdAt = createdAt;
        this.inativeAt = inativeAt;
        this.notes = notes;
    }

    public CreditCardInvoiceResponseDto getInvoice() {
        return invoice;
    }

    public void setInvoice(CreditCardInvoiceResponseDto invoice) {
        this.invoice = invoice;
    }

    public CreditCardResponseDto getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardResponseDto creditCard) {
        this.creditCard = creditCard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public CostCenterResponseDto getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenterResponseDto costCenter) {
        this.costCenter = costCenter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}