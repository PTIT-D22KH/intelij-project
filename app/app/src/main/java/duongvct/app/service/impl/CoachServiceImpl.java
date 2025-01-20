package duongvct.app.service.impl;

import duongvct.app.entity.Coach;
import duongvct.app.exception.ResourceNotFoundException;
import duongvct.app.repository.CoachRepository;
import duongvct.app.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CoachServiceImpl implements CoachService {
    private CoachRepository coachRepository;

    @Autowired
    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }


    @Override
    public void addCoach(Coach coach) {
        coachRepository.save(coach);
    }

    @Override
    public void updateCoach(int id, Coach coach) {
        Coach curr = coachRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Coach with id " + id + " not found"));
        curr.setAge(coach.getAge());
        curr.setCountry(coach.getCountry());
        curr.setName(coach.getName());
        curr.setTeam(coach.getTeam());
        coachRepository.save(curr);
    }

    @Override
    public void deleteCoach(int id) {
        coachRepository.deleteById(id);
    }

    @Override
    public Coach findById(int id) {
        return coachRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Coach with id " + id + " not found"));
    }

    @Override
    public Page<Coach> findAllCoaches(Pageable pageable, String col, String key) {
        if (key != null && col != null && !key.isBlank()) {
            switch (col) {
                case "name":
                    return coachRepository.findByNameContainingIgnoreCase(key, pageable);
                case "country":
                    return coachRepository.findByCountryContainingIgnoreCase(key, pageable);
                case "age":
                    try {
                        int age = Integer.parseInt(key);
                        return coachRepository.findByAge(age, pageable);
                    } catch (Exception exception) {
                        return coachRepository.findAll(pageable);
                    }
                default:
                    return coachRepository.findAll(pageable);

            }
        }
        return coachRepository.findAll(pageable);
    }
}
