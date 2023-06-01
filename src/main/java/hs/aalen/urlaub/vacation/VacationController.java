package hs.aalen.urlaub.vacation;

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
public class VacationController {

  //----connection to VacationService class------------------
  @Autowired
  VacationService vacationService;

  //-------------------------------------------------------
  //-------URL mapping-------------------------------------

  @GetMapping("/api/vacation")
  public List<Vacation> getVacationList() {
    return vacationService.getVacationList();
  }

  @GetMapping("/api/vacation/{id}")
  public Vacation getVacation(@PathVariable long id) {
    return vacationService.getVacation(id);
  }

  @PostMapping("/api/vacation")
  public void addVacation(@RequestBody Vacation vacation) {
    vacationService.addVacation(vacation);
  }

  @PutMapping("/api/vacation/{id}")
  public void updateVacation(
    @PathVariable long id,
    @RequestBody Vacation vacation
  ) {
    vacationService.updateVacation(id, vacation);
  }

  @DeleteMapping("/api/vacation/{id}")
  public void deleteVacation(@PathVariable long id) {
    vacationService.deleteVacation(id);
  }
  //------------------------------------------------------
}
