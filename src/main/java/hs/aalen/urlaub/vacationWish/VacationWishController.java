package hs.aalen.urlaub.vacationWish;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacationWishController {

  //----connection to VacationWishService class------------------
  @Autowired
  VacationWishService vacationWishService;

  //-------------------------------------------------------
  //-------URL mapping-------------------------------------
  @GetMapping("/api/vacationWish")
  public List<VacationWish> getVacationWishList() {
    return vacationWishService.getVacationWishList();
  }

  @GetMapping("/api/vacationWish/{id}")
  public VacationWish getVacationWish(@PathVariable long id) {
    return vacationWishService.getVacationWish(id);
  }

  @PostMapping("/api/vacationWish")
  public void addVacationWish(@RequestBody VacationWish vacationWish) {
    vacationWishService.addVacationWish(vacationWish);
  }

  @PutMapping("/api/vacationWish/{id}")
  public void updateVacationWish(
    @PathVariable long id,
    @RequestBody VacationWish vacationWish
  ) {
    vacationWishService.updateVacationWish(id, vacationWish);
  }

  @DeleteMapping("/api/vacationWish/{id}")
  public void deleteVacationWish(@PathVariable long id) {
    vacationWishService.deleteVacationWish(id);
  }
  //------------------------------------------------------

}
