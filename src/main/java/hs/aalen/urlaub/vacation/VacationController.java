package hs.aalen.urlaub.vacation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


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

  @PutMapping("/api/vacation/conclude/{id}")
public void concludeVacation(@PathVariable long id) {
  vacationService.concludeVacation(id);
}
  //------------------------------------------------------
  //-------Routes for Thymleaf-------------------------------------
  @GetMapping("/vacation")
  public ModelAndView showVacation() {
    ModelAndView mav = new ModelAndView("list-vacation");
    mav.addObject("vacations", getVacationList());
    return mav;
  }

  @GetMapping("/addVacation")
  public ModelAndView addVacation() {
    ModelAndView mav = new ModelAndView("add-vacation-form");
    Vacation newVacation = new Vacation();
    mav.addObject("vacation", newVacation);
    return mav;
  }

  @PostMapping("/saveVacation")
  public RedirectView saveVacation(@ModelAttribute Vacation vacation) {
    addVacation(vacation);
    return new RedirectView("/vacation");
  }

  @GetMapping("/updateVacation")
  public ModelAndView updateVacation(@RequestParam Long vacationId) {
    ModelAndView mav = new ModelAndView("add-vacation-form");
    mav.addObject("vacation", getVacation(vacationId));
    return mav;
  }

  @GetMapping("/deleteVacation")
  public RedirectView deleteVacations(@RequestParam Long vacationId) {
    deleteVacation(vacationId);
    return new RedirectView("/vacation");
  }

  //------------------------------------------------------
}
