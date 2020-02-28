package Application.repo;

import Application.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query
    List<Product> findAllByMerchantId(int merchantID);

    @Override
    void deleteById(Integer integer);
}
