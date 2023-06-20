package hs.aalen.urlaub.vacation;

import java.util.List;


import hs.aalen.urlaub.vacationWish.VacationWish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Comparator;
import java.util.stream.Collectors;

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

  @GetMapping("/concludeVacation")
  public ModelAndView concludeVacation(@RequestParam Long vacationId) {
    ModelAndView mav = new ModelAndView("conclude-vacation");
    mav.addObject("vacation", getVacation(vacationId));
    return mav;
  }

  @GetMapping("/sortedWishes")
  public ModelAndView getSortedWishes(@RequestParam Long vacationId) {
    ModelAndView mav = new ModelAndView("conclude-vacation");
    mav.addObject("sortedWishes", sortWishesByRating(vacationId));
    return mav;
  }

  private List<VacationWish> sortWishesByRating(Long vacationId) {
    List<VacationWish> allWishes = vacationService.getVacation(vacationId).getVacationWishes();

    // Sortiere die Wünsche absteigend nach der Bewertung
    List<VacationWish> sortedWishes = allWishes.stream()
            .sorted(Comparator.comparing(VacationWish::getRating).reversed())
            .collect(Collectors.toList());

    return sortedWishes;
  }


  //------------------------------------------------------
}
