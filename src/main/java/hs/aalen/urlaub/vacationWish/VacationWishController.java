package hs.aalen.urlaub.vacationWish;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

  @GetMapping("/api/wishToVacation/{vacationId}")
  public List<VacationWish> getVacationWishListToVacation(@PathVariable Long vacationId) {
    //TODO: Implementation
    return null;
  }
  @GetMapping("/api/sortedWishes/{vacationId}")
  public List<VacationWish> getSortedWishes(@PathVariable Long vacationId) {
    return vacationWishService.getSortedWishes(vacationId);
  }

  //------------------------------------------------------
  //-------Routes for Thymleaf-------------------------------------
  @GetMapping("/wish")
  public ModelAndView showVacationWish() {
    ModelAndView mav = new ModelAndView("lsit-vacationWish");
    mav.addObject("wishes", getVacationWishList());
    return mav;
  }

  @GetMapping("/addWish")
  public ModelAndView addWish() {
    ModelAndView mav = new ModelAndView("add-wish-form");
    VacationWish newWish = new VacationWish();
    mav.addObject("wish", newWish);
    return mav;
  }

  @PostMapping("/saveWish")
  public RedirectView saveWish(@ModelAttribute VacationWish wish) {
    addVacationWish(wish);
    return new RedirectView("/wish");
  }

  @GetMapping("/updateWish")
  public ModelAndView updateWish(@RequestParam Long wishId) {
    ModelAndView mav = new ModelAndView("add-wish-form");
    mav.addObject("wish", getVacationWish(wishId));
    return mav;
  }

  @GetMapping("/deleteWish")
  public RedirectView deleteWish(@RequestParam Long wishId) {
    deleteVacationWish(wishId);
    return new RedirectView("/wish");
  }

  @GetMapping("/vote")
  public ModelAndView vote(@RequestParam long vacationId) {
    ModelAndView mav = new ModelAndView("vote");
    mav.addObject("wishes", getVacationWishListToVacation(vacationId));
    return mav;
  }
  //------------------------------------------------------


}
