package duongvct.app.repository;

import duongvct.app.entity.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CoachRepository extends JpaRepository<Coach, Integer>, PagingAndSortingRepository<Coach, Integer> {
    public Page<Coach> findByNameContainingIgnoreCase(String key, Pageable pageable);

    public Page<Coach> findByCountryContainingIgnoreCase(String key, Pageable pageable);
    public Page<Coach> findByAge(int age, Pageable pageable);


}
