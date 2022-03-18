package com.example.ecommerce.Models;

public class Customer {
    public String Name , Username , Password , BirthDate , Gender , Job , Question , Answer;
    public Customer(String name, String username, String password, String birthDate, String gender, String job , String question , String answer) {
        Name = name;
        Username = username;
        Password = password;
        BirthDate = birthDate;
        Gender = gender;
        Job = job;
        Question = question;
        Answer = answer;
    }
}
