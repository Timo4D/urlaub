package hs.aalen.urlaub.vacation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationService {

  @Autowired
  VacationRepository vacationRepository;

  public List<Vacation> getVacationList() {
    ArrayList<Vacation> vacationList = new ArrayList<>();
    Iterator<Vacation> iterator = vacationRepository.findAll().iterator();
    while (iterator.hasNext()) vacationList.add(iterator.next());
    return vacationList;
  }

  public Vacation getVacation(long id) {
    return vacationRepository.findById(id).orElse(null);
  }

  public void addVacation(Vacation vacation) {
    vacationRepository.save(vacation);
  }

  public void updateVacation(long id, Vacation vacation) {
    vacationRepository.save(vacation);
  }

  public void deleteVacation(long id) {
    vacationRepository.deleteById(id);
  }
}
