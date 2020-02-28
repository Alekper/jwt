package Application.repo;

import Application.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MerchantRepo extends JpaRepository<Merchant,Integer> {
    @Query
    Optional<Merchant> findByEmail(String email);

    @Query
    Optional<Merchant> findByToken(String token);

    @Override
    <S extends Merchant> S save(S entity);
}
