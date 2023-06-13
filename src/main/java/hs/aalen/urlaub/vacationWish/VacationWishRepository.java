package hs.aalen.urlaub.vacationWish;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VacationWishRepository extends CrudRepository<VacationWish, Long> {
     List<VacationWish> findByVacationId(Long vacationId);
}
