package org.roka.ecomerce.repo;

import org.roka.ecomerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

   List<Order> findByUserEmail(String userEmail);
}
