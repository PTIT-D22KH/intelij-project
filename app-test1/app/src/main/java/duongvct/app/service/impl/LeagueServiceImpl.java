package duongvct.app.service.impl;

import duongvct.app.entity.League;
import duongvct.app.entity.LeagueTable;
import duongvct.app.exception.ResourceNotFoundException;
import duongvct.app.repository.LeagueRepository;
import duongvct.app.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    private LeagueRepository leagueRepository;

    @Autowired
    public LeagueServiceImpl(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public Page<League> findAll(Pageable pageable) {
        return leagueRepository.findAll(pageable);
    }

    @Override
    public List<League> findAll() {
        return leagueRepository.findAll();
    }

    @Override
    public League findById(int id) {
        return leagueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("League with id = " + id + "not found"));
    }

    @Override
    public void updateLeague(int id, League league) {
        League curr = findById(id);
        curr.setName(league.getName());
        curr.setNumRounds(league.getNumRounds());
        leagueRepository.save(curr);
    }

    @Override
    public void deleteLeague(int id) {
        leagueRepository.deleteById(id);
    }

    @Override
    public void createLeague(League league) {
        // Save the league first to generate the ID
        League savedLeague = leagueRepository.save(league);

        // Create and set the LeagueTable
        LeagueTable leagueTable = new LeagueTable();
        leagueTable.setLeague(savedLeague);
        savedLeague.setLeagueTable(leagueTable);

        // Save the league again with the LeagueTable
        leagueRepository.save(savedLeague);
    }
}
