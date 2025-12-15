package org.roka.ecomerce.service;

import org.roka.ecomerce.dto.OrderRequest;
import org.roka.ecomerce.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    public void placeOrder(OrderRequest request, String userEmail);

    public List<OrderResponse> getAccOrders(String userEmail);
}
