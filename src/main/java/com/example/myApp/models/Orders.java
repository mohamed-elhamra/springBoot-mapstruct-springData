package com.example.myApp.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.DATE)
    @NotNull(message = "Should not be empty")
    private Date date;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Customer customer;
    @ManyToMany(mappedBy = "ordersList", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Product> productList = new ArrayList<>();

}
