package hs.aalen.urlaub.vacationWish;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VacationWishRepository extends CrudRepository<VacationWish, Long> {
    List<VacationWish> findByVacationId(Long vacationId);
}
