package hs.aalen.urlaub.vacation;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberService;
import hs.aalen.urlaub.rating.RatingService;
import hs.aalen.urlaub.vacationWish.VacationWish;
import hs.aalen.urlaub.vacationWish.VacationWishAndRating;
import hs.aalen.urlaub.vacationWish.VacationWishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class VacationController {


    @Autowired
    VacationService vacationService;

    @Autowired
    MemberService memberService;

    @Autowired
    VacationWishService vacationWishService;

    @Autowired
    RatingService ratingService;

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
        mav.addObject("members", memberService.getMemberList());
        return mav;
    }

    @PostMapping("/saveVacation")
    public RedirectView saveVacation(@ModelAttribute Vacation vacation, @RequestParam String selectedMemberIds) {
        List<Long> memberIds = Arrays.stream(selectedMemberIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        List<Member> members = memberService.getAllMembersById(memberIds);

        vacation.setMemberAccess(members);
        vacationService.addVacation(vacation);

        return new RedirectView("/vacation");
    }

    @GetMapping("/deleteVacation")
    public RedirectView deleteVacations(@RequestParam Long vacationId) {
        deleteVacation(vacationId);
        return new RedirectView("/vacation");
    }

    @GetMapping("/conclude")
    public ModelAndView conclude(@RequestParam Long vacationId) {
        ModelAndView mav = new ModelAndView("conclude-vacation");
        mav.addObject("vacation", getVacation(vacationId));
        List<VacationWish> wishes = vacationWishService.getVacationWishByVacationId(vacationId);
        List<VacationWishAndRating> wishAndRatings = new ArrayList<>();
        wishes.forEach(wish -> {
            Double rating = ratingService.getAverageRatingForVacationWish(wish.getId());
            wishAndRatings.add(new VacationWishAndRating(wish, rating));

        });
        mav.addObject("wishes", wishAndRatings);

        return mav;
    }

    @GetMapping("/concludeVacation/{vacationId}")
    public RedirectView concludeVacation(@PathVariable Long vacationId) {
        vacationService.concludeVacation(vacationId);
        return new RedirectView("/vacation");
    }
}
