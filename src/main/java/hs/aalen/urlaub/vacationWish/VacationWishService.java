package hs.aalen.urlaub.vacationWish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class VacationWishService {

  //----connection to VacationWishRepository class------------------
  @Autowired
  VacationWishRepository vacationWishRepository;

  //---------------------------------------------------------
  public List<VacationWish> getVacationWishList() {
    ArrayList<VacationWish> vacationWishList = new ArrayList<>();
    Iterator<VacationWish> iterator = vacationWishRepository
      .findAll()
      .iterator();
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

  public List<VacationWish> getSortedWishes(Long vacationId) {
    // Rufen Sie die VacationWish-Objekte für den angegebenen Urlaub ab
    List<VacationWish> vacationWishes = VacationWishRepository.findByVacationId(vacationId);

    // Sortieren Sie die Wünsche absteigend nach der Bewertung
    List<VacationWish> sortedWishes = vacationWishes.stream()
            .sorted(Comparator.comparing(VacationWish::getRating).reversed())
            .collect(Collectors.toList());

    return sortedWishes;
  }
}

