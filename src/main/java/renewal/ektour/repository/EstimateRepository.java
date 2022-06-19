package renewal.ektour.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import renewal.ektour.domain.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    @Query("SELECT e FROM Estimate e WHERE e.visibility = true")
    Page<Estimate> findAll(Pageable pageable);

    @Query("SELECT COUNT(e) FROM Estimate e")
    int countAll();
}
