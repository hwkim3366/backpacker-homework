package com.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByOwnerUid(Long ownerUid);
	
	Order save(Order order);
    
}
