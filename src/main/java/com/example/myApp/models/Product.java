package com.example.myApp.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Should not be empty")
    @Size(min = 5,max = 20, message = "The size must be between 5 and 20")
    private String name;
    @NotNull(message = "Should not be empty")
    private int quantity;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Orders> ordersList = new ArrayList<>();

}
