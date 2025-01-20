package duongvct.app.service.impl;

import duongvct.app.entity.Player;
import duongvct.app.exception.ResourceNotFoundException;
import duongvct.app.repository.PlayerRepository;
import duongvct.app.repository.TeamRepository;
import duongvct.app.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
//    private TeamRepository teamRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Page<Player> findAllPlayers(Pageable pageable, String col, String key) {
        if (key != null && col != null && !key.isBlank()) {
            switch (col) {
                case "name":
                    return playerRepository.findByNameContainingIgnoreCase(key, pageable);
                case "country":
                    return playerRepository.findByCountryContainingIgnoreCase(key, pageable);
                case "position":
                    return playerRepository.findByPositionContainingIgnoreCase(key, pageable);
                case "age":
                    try {
                        int age = Integer.parseInt(key);
                        return playerRepository.findByAge(age, pageable);
                    } catch (Exception exception) {
                        return playerRepository.findAll(pageable);
                    }
                default:
                    return playerRepository.findAll(pageable);

            }
        }
        return playerRepository.findAll(pageable);
    }

    @Override
    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public void updatePlayer(int id, Player player) {
        Player currentPlayer = playerRepository.findById(id).orElse(null);
        if (currentPlayer == null) {
            throw new ResourceNotFoundException("Player not found with id: " + id);
        }
        currentPlayer.setName(player.getName());
        currentPlayer.setCountry(player.getCountry());
        currentPlayer.setAge(player.getAge());
        currentPlayer.setPosition(player.getPosition());
        currentPlayer.setTeam(player.getTeam());
        playerRepository.save(currentPlayer);
    }

    @Override
    public void deletePlayer(int id) {
//        Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player with id " + id + " not found"));
        playerRepository.deleteById(id);
    }

    @Override
    public Player findById(int id) {
        return playerRepository.findById(id).orElse(null);
    }

}
