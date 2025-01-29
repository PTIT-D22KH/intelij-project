package duongvct.app.service;

import duongvct.app.entity.Coach;
import duongvct.app.repository.CoachRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoachService {
    public void addCoach(Coach coach);
    public void updateCoach(int id, Coach coach);

    public void deleteCoach(int id);

    public Coach findById(int id);

    public Page<Coach> findAllCoaches(Pageable pageable, String col, String key);
}
