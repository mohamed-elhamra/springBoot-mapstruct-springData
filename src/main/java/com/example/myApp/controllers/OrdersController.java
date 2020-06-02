package com.example.myApp.controllers;

import com.example.myApp.dtos.OrdersDto;
import com.example.myApp.exceptions.ResourceNotFoundException;
import com.example.myApp.mapper.MyMapper;
import com.example.myApp.models.Customer;
import com.example.myApp.models.Orders;
import com.example.myApp.repositories.CustomerRepository;
import com.example.myApp.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myapp")
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MyMapper myMapper;

    @PostMapping("/orders")
    public OrdersDto saveOrders(@RequestBody OrdersDto ordersDto) throws ResourceNotFoundException {
        Orders orders = myMapper.toOrders(ordersDto);
        Customer customer = customerRepository.findById(ordersDto.getCustomer())
                            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id: " + ordersDto.getCustomer()));
        orders.setCustomer(customer);
        ordersRepository.save(orders);
        return ordersDto;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrdersDto> getOrdersByID(@PathVariable(name ="id") Long id) throws ResourceNotFoundException {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orders not found with this id: " + id));
        OrdersDto ordersDto = myMapper.toOrdersDto(orders);
        return ResponseEntity.ok().body(ordersDto);
    }

    @GetMapping("/orders")
    public List<OrdersDto> getAllOrders(){
        return myMapper.toOrdersDtoList(ordersRepository.findAll());
    }

    @DeleteMapping("/orders/{id}")
    public Map<String, Boolean> deleteOrders(@PathVariable Long id) throws ResourceNotFoundException {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with this id: " + id));
        ordersRepository.delete(orders);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }


}
