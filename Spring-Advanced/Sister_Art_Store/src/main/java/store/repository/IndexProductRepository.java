package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.model.entity.IndexProduct;

@Repository
public interface IndexProductRepository extends JpaRepository<IndexProduct, String> {
}
