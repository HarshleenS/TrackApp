package com.example.trackyourpackage.model;

public class Customer
{private String name,phone,password,email;
   public Customer()
    {

    }

    public Customer(String name,String email,String password,String phone)
    {
        this.name = name;
        this.phone=phone;
        this.email=email;
        this.password=password;
    }

    public String getName()
    {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
