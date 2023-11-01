package com.cqrsaxon.practice.productservice.repository;

import com.cqrsaxon.practice.productservice.collection.ProductLookUpCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLookUpRepository extends MongoRepository<ProductLookUpCollection, String> {
    ProductLookUpCollection findByProductIdOrTitle(String productId, String title);
}
