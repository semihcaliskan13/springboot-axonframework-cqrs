package com.cqrsaxon.practice.productservice.repository;

import com.cqrsaxon.practice.productservice.collection.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByProductId(String productId);

    Optional<Product> findByProductIdOrTitle(String productId, String title);

    Optional<Product> findAllByPriceLessThan(BigDecimal price);

}
