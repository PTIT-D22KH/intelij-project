package duongvct.app.service;

import duongvct.app.entity.League;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeagueService {
    Page<League> findAll(Pageable pageable);

    List<League> findAll();

    League findById(int id);

    void updateLeague(int id, League league);
    void deleteLeague(int id);

    void createLeague(League league);
}
