package org.roka.ecomerce.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Min(value = 0)
    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    Status status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;



}
