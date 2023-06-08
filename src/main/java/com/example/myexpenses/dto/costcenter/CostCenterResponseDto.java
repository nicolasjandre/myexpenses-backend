package com.example.myexpenses.dto.costcenter;

import java.util.Date;

import com.example.myexpenses.domain.enums.Type;

public class CostCenterResponseDto {
    private Long id;

    private String description;

    private boolean standard;

    private Type type;

    private Date inative_at;

    public CostCenterResponseDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public CostCenterResponseDto(Long id, String description, boolean standard, Type type, Date inative_at) {
        this.id = id;
        this.description = description;
        this.standard = standard;
        this.type = type;
        this.inative_at = inative_at;
    }

    public CostCenterResponseDto() {
    }

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

    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getInative_at() {
        return inative_at;
    }

    public void setInative_at(Date inative_at) {
        this.inative_at = inative_at;
    }

}
