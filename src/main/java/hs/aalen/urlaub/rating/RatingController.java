package hs.aalen.urlaub.rating;


import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@RestController
public class RatingController {

    @Autowired
    RatingService ratingService;

    @Autowired
    MemberService memberService;

    @GetMapping("/api/ratings")
    public List<Rating> getRatings() {
        return ratingService.getRatings();
    }


    @PostMapping("/api/member/{memberId}/vacationWish/{vacationWishId}/rating")
    public Rating createRating(
            @PathVariable Long memberId,
            @PathVariable Long vacationWishId,
            @RequestBody int score
    ) {
        return ratingService.createRating(memberId, vacationWishId, score);
    }

    @GetMapping("/api/member/{memberId}/vacationWish/{vacationWishId}/rating")
    public Rating getRatingByMemberAndVacationWish(@PathVariable Long memberId, @PathVariable Long vacationWishId) {
        return ratingService.findByMemberIdAndVacationWishId(memberId, vacationWishId);
    }

    //Thymleaf Routes

    @PostMapping("/saveRatings")
    public RedirectView saveRatings(@ModelAttribute RatingListWrapper ratingListWrapper, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        ratingListWrapper.getRatings().forEach(rating -> {
            rating.setMember(member);
            ratingService.saveRating(rating);
        });

        return new RedirectView("/vacation");
    }
}

