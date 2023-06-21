package hs.aalen.urlaub.vacationWish;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberService;
import hs.aalen.urlaub.rating.Rating;
import hs.aalen.urlaub.rating.RatingListWrapper;
import hs.aalen.urlaub.rating.RatingService;
import hs.aalen.urlaub.vacation.Vacation;
import hs.aalen.urlaub.vacation.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@RestController
public class VacationWishController {

    @Autowired
    VacationWishService vacationWishService;
    @Autowired
    VacationService vacationService;

    @Autowired
    MemberService memberService;

    @Autowired
    RatingService ratingService;

    //-------URL mapping-------------------------------------
    @GetMapping("/api/vacationWish")
    public List<VacationWish> getVacationWishList() {
        return vacationWishService.getVacationWishList();
    }

    @GetMapping("/api/{vacationId}/wishes")
    public List<VacationWish> getAllWishesForVacation(@PathVariable Long vacationId) {
        return vacationWishService.getVacationWishListToVacation(vacationId);
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
        return vacationWishService.getVacationWishListToVacation(vacationId);
    }

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
        mav.addObject("vacations", vacationService.getVacationList());
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
    public ModelAndView vote(@RequestParam long vacationId, Principal principal) {
        ModelAndView mav = new ModelAndView("vote");

        Member member = memberService.getMember(principal.getName());
        Vacation vacation = vacationService.getVacation(vacationId);
        List<VacationWish> wishes = vacationWishService.getVacationWishByVacationId(vacationId);
        List<Rating> ratings = ratingService.getRatingsByMemberId(member.getId());
        RatingListWrapper ratingListWrapper = new RatingListWrapper(ratings);

        mav.addObject("member", member);
        mav.addObject("wishes", wishes);
        mav.addObject("vacation", vacation);
        mav.addObject("ratingListWrapper", ratingListWrapper);

        return mav;
    }
    //------------------------------------------------------
}
