package com.zerobase.fitme.repository;

import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Model;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
