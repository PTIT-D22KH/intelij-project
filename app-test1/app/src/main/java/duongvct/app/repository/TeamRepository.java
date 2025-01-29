package duongvct.app.repository;

import duongvct.app.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TeamRepository extends JpaRepository<Team, Integer> {
    public Page<Team> findByNameContainingIgnoreCase(String key, Pageable pageable);

    public Page<Team> findByCountryContainingIgnoreCase(String key, Pageable pageable);

    public Page<Team> findByRanked(int ranked, Pageable pageable);



}
