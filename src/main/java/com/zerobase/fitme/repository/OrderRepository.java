package com.zerobase.fitme.repository;

import com.zerobase.fitme.entity.Order;
import com.zerobase.fitme.type.OrderStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember_Username(String username);

    Page<Order> findByOrderStatusOrderByIdDesc(OrderStatus status, Pageable pageable);
}
