package hs.aalen.urlaub.rating;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RatingController {

  @Autowired
  RatingService ratingService;

  @PostMapping("/api/rateVacationWish")
  public void rateVacationWish(@RequestBody Rating rating) {
    ratingService.addRating(rating);
  }

    @GetMapping("/api/member/{memberId}/vacationWish/{vacationWishId}/rating")
    public Rating getRatingByMemberAndVacationWish(@PathVariable Long memberId, @PathVariable Long vacationWishId) {
        return ratingService.findByMemberIdAndVacationWishId(memberId, vacationWishId);
    }
}

