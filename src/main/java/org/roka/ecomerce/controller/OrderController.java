package org.roka.ecomerce.controller;


import lombok.RequiredArgsConstructor;
import org.roka.ecomerce.dto.OrderRequest;
import org.roka.ecomerce.dto.OrderResponse;
import org.roka.ecomerce.entity.Order;
import org.roka.ecomerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequest req){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        orderService.placeOrder(req, email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return ResponseEntity.ok(orderService.getAccOrders(email));
    }
}
