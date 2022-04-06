package it.app.mobile.repository;

import it.app.mobile.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductEntityRepository extends JpaRepository<Product,Integer> {
}
