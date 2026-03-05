package com.ansarishop.app.repository;

import com.ansarishop.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    @Query("""
            UPDATE Product p SET p.stock = p.stock - :qnt
            where p.id = :id
            """)
    int updateStock(long id, long qnt);

    List<Product> findByStock(long stock);
}
