package org.roka.ecomerce.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.roka.ecomerce.dto.OrderItemRequest;
import org.roka.ecomerce.dto.OrderItemResponse;
import org.roka.ecomerce.dto.OrderRequest;
import org.roka.ecomerce.dto.OrderResponse;
import org.roka.ecomerce.entity.*;
import org.roka.ecomerce.exception.ResourceNotFoundException;
import org.roka.ecomerce.repo.OrderRepo;
import org.roka.ecomerce.repo.ProductRepo;
import org.roka.ecomerce.repo.UserRepo;
import org.roka.ecomerce.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    @Transactional
    public void placeOrder(OrderRequest request, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + userEmail)
        );

        Order order = new Order();

        order.setUser(user);
        order.setStatus(Status.PENDING);

        List<OrderItem> orderedItems = new ArrayList<>();
        for(OrderItemRequest orderItemReq : request.items()){
            Product product = productRepo.findById(orderItemReq.productId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product not found with id: " + orderItemReq.productId())
            );
            if((product.getStock() < orderItemReq.quantity())){
                throw new ResourceNotFoundException("Insufficient stock " + orderItemReq.productId());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);


            orderItem.setQuantity(orderItemReq.quantity());
            orderItem.setOrder(order);
            orderItem.setPrice(product.getPrice());

            orderedItems.add(orderItem);

            //updating the stock
            product.setStock(product.getStock() - orderItemReq.quantity());
            productRepo.save(product);
        }

        order.setOrderItems(orderedItems);

        BigDecimal totalPrice = new BigDecimal(0);
        for(OrderItem o : orderedItems){
            totalPrice = totalPrice.add(o.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())));
        }
        order.setTotalPrice(totalPrice);

        orderRepo.save(order);

    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAccOrders(String userEmail) {
        List<Order> orders =  orderRepo.findByUserEmail(userEmail);

        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orders){
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItemResponse> orderItemResponses = new ArrayList<>();

            for(OrderItem o : orderItems){
                OrderItemResponse or = new OrderItemResponse(
                        o.getId(),
                        o.getPrice(),
                        o.getQuantity(),
                        o.getProduct().getName(),
                        o.getProduct().getId()
                );

                orderItemResponses.add(or);
            }

            orderResponses.add(new OrderResponse(
                    order.getId(),
                    order.getTotalPrice(),
                    order.getStatus(),
                    order.getCreatedAt(),
                    orderItemResponses

            ));

        }



        return orderResponses;
    }
}
