package com.example.myApp.controllers;

import com.example.myApp.dtos.CustomerDto;
import com.example.myApp.dtos.OrdersDto;
import com.example.myApp.exceptions.ResourceNotFoundException;
import com.example.myApp.mapper.MyMapper;
import com.example.myApp.models.Customer;
import com.example.myApp.models.Orders;
import com.example.myApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myapp")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MyMapper myMapper;

    @PostMapping("/customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = myMapper.toCustomer(customerDto);
        customerRepository.save(customer);
        return customerDto;
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomerByID(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id: " + id));
        CustomerDto customerDto = myMapper.toCustomerDto(customer);
        return ResponseEntity.ok().body(customerDto);
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers(){
        return myMapper.toCustomerDtoList(customerRepository.findAll());
    }

    @DeleteMapping("/customer/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id: " + id));
        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,
                                                      @RequestBody CustomerDto customerDto) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id: " + id));
        Customer customer1 = myMapper.toCustomer(customerDto);
        customer.setName(customer1.getName());
        customer.setEmail(customer1.getEmail());
        final Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(myMapper.toCustomerDto(updatedCustomer));
    }

    @GetMapping("/customers/{id}/orders")
    public List<OrdersDto> getAllOrders(@PathVariable Long id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with this id: " + id));
        List<OrdersDto> ordersDtoList = myMapper.toOrdersDtoList(customer.getOrdersList());
        return ordersDtoList;
    }


}
