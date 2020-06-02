package com.example.myApp.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Should not be empty")
    @Size(min = 5,max = 20, message = "The size must be between 5 and 20")
    private String name;
    @NotEmpty(message = "Should not be empty")
    @Email(message = "Email format not valid")
    private String email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Orders> ordersList = new ArrayList<>();

}
