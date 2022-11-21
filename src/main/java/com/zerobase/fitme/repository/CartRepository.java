package com.zerobase.fitme.repository;

import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember_Username(String username);
    List<Cart> findByMember_IdAndItem_Id(Long memberId, Long itemId);
}
