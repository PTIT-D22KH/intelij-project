package duongvct.app.service;

import duongvct.app.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerService {
    public Page<Player> findAllPlayers(Pageable pageable, String col, String key);

    public void addPlayer(Player player);

    public void updatePlayer(int id, Player player);

    public void deletePlayer(int id);

    public Player findById(int id);
}
