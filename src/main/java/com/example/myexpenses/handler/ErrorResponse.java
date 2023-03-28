package com.example.myexpenses.handler;

public class ErrorResponse {
    
    private String dateAndHour;

    private Integer status;

    private String title;
    
    private String message;

    public ErrorResponse(String dateAndHour, Integer status, String title, String message) {
        this.dateAndHour = dateAndHour;
        this.status = status;
        this.title = title;
        this.message = message;
    }

    public String getdateAndHour() {
        return dateAndHour;
    }

    public void setdateAndHour(String dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
