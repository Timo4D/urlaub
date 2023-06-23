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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public ModelAndView showVacationWish(Principal principal) {
        ModelAndView mav = new ModelAndView("list-vacationWish");
//        mav.addObject("wishes", getVacationWishList());
        Long authorId = memberService.getMember(principal.getName()).getId();
        mav.addObject("wishes", vacationWishService.getVacationWishListToAuthorId(authorId));
        return mav;
    }

    @GetMapping("/addWish")
    public ModelAndView addWish(Principal principal) {
        ModelAndView mav = new ModelAndView("add-wish-form");
        VacationWish newWish = new VacationWish();
        Member member = memberService.getMember(principal.getName());
        mav.addObject("wish", newWish);
        mav.addObject("vacations", vacationService.getVacationByMemberAccessIsContaining(member));
        return mav;
    }

    @PostMapping("/saveWish")
    public RedirectView saveWish(@ModelAttribute VacationWish wish, Principal principal) {
        wish.setAuthorId(memberService.getMember(principal.getName()).getId());
        addVacationWish(wish);
        return new RedirectView("/wish");
    }

    @GetMapping("/updateWish")
    public ModelAndView updateWish(@RequestParam Long wishId, Principal principal) {
        ModelAndView mav = new ModelAndView("add-wish-form");
        Member member = memberService.getMember(principal.getName());
        mav.addObject("wish", getVacationWish(wishId));
        mav.addObject("vacations", vacationService.getVacationByMemberAccessIsContaining(member));
        return mav;
    }

    @GetMapping("/deleteWish")
    public RedirectView deleteWish(@RequestParam Long wishId) {
        deleteVacationWish(wishId);
        return new RedirectView("/wish");
    }

    @GetMapping("/updateVacation")
    public ModelAndView updateVacation(@RequestParam Long vacationId) {
        ModelAndView mav = new ModelAndView("add-vacation-form");
        mav.addObject("vacation", vacationService.getVacation(vacationId));
        mav.addObject("members", memberService.getMemberList());
        return mav;
    }

    @GetMapping("/vote")
    public ModelAndView vote(@RequestParam long vacationId, Principal principal) {
        ModelAndView mav = new ModelAndView("vote");

        Member member = memberService.getMember(principal.getName());
        Vacation vacation = vacationService.getVacation(vacationId);
        List<VacationWish> wishes = vacationWishService.getVacationWishByVacationId(vacationId);
        List<Rating> ratings = ratingService.getRatingsByMemberId(member.getId());
        RatingListWrapper ratingListWrapper = new RatingListWrapper(ratings);

        Map<Long, Rating> ratingMap = ratings.stream()
                .collect(Collectors.toMap(rating -> rating.getVacationWish().getId(), Function.identity()));

        mav.addObject("member", member);
        mav.addObject("wishes", wishes);
        mav.addObject("vacation", vacation);
        mav.addObject("ratingListWrapper", ratingListWrapper);
        mav.addObject("ratingMap", ratingMap);

        return mav;
    }
    //------------------------------------------------------
}
