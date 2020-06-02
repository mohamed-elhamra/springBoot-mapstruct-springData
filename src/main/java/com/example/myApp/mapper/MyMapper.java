package com.example.myApp.mapper;

import com.example.myApp.dtos.CustomerDto;
import com.example.myApp.dtos.OrdersDto;
import com.example.myApp.dtos.ProductDto;
import com.example.myApp.models.Customer;
import com.example.myApp.models.Orders;
import com.example.myApp.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MyMapper {

    CustomerDto toCustomerDto(Customer customer);

    Customer toCustomer(CustomerDto customerDto);

    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "customer", source=  "customer.id")
    OrdersDto toOrdersDto(Orders orders);

    @Mapping(target = "customer", source = "customer", ignore = true)
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    Orders toOrders(OrdersDto ordersDto);

    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

    List<CustomerDto> toCustomerDtoList(List<Customer> customers);

    List<OrdersDto> toOrdersDtoList(List<Orders> orders);

    List<ProductDto> toProductDtoList(List<Product> products);

}
