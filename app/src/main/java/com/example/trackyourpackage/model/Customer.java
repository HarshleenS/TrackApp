package com.example.trackyourpackage.model;

public class Customer
{private String name,phone,password,email,image;

    public Customer()
    {

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Customer(String name, String phone, String password, String email, String image) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.image = image;
    }
}
