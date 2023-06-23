package hs.aalen.urlaub.vacation;

import hs.aalen.urlaub.member.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VacationRepository extends CrudRepository<Vacation, Long> {

    List<Vacation> getVacationByMemberAccessIsContaining(Member member);
}
