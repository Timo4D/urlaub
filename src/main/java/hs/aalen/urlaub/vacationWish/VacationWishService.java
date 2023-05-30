package hs.aalen.urlaub.vacationWish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationWishService {
    
    @Autowired
  VacationWishRepository vacationWishRepository;

  public List<VacationWish> getVacationWishList() {
    ArrayList<VacationWish> vacationWishList = new ArrayList<>();
    Iterator<VacationWish> iterator = vacationWishRepository.findAll().iterator();
    while (iterator.hasNext()) vacationWishList.add(iterator.next());
    return vacationWishList;
  }

  public VacationWish getVacationWish(long id) {
    return vacationWishRepository.findById(id).orElse(null);
  }

  public void addVacationWish(VacationWish vacationWish) {
    vacationWishRepository.save(vacationWish);
  }

  public void updateVacationWish(long id, VacationWish vacationWish) {
    vacationWishRepository.save(vacationWish);
  }

  public void deleteVacationWish(long id) {
    vacationWishRepository.deleteById(id);
  }
}
