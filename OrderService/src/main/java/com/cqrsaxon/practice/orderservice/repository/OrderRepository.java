package com.cqrsaxon.practice.orderservice.repository;

import com.cqrsaxon.practice.orderservice.collection.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
}
