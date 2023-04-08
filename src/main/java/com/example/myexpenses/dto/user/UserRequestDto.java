package com.example.myexpenses.dto.user;

public class UserRequestDto {

    private String name;
    
    private String email;

    private Double userBalance;

    private String image;

    private String password;

    private String newPassword;

    private String passwordConfirmation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public Double getUserBalance() {
        return this.userBalance;
    }

   public String getNewPassword() {
      return newPassword;
   }

   public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }
}
