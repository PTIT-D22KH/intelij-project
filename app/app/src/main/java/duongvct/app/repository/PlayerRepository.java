package duongvct.app.repository;

import duongvct.app.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer>, PagingAndSortingRepository<Player, Integer> {
    public Page<Player> findByNameContainingIgnoreCase(String key, Pageable pageable);

    public Page<Player> findByCountryContainingIgnoreCase(String key, Pageable pageable);

    public Page<Player> findByPositionContainingIgnoreCase(String key, Pageable pageable);

    public Page<Player> findByAge(int key, Pageable pageable);


}
