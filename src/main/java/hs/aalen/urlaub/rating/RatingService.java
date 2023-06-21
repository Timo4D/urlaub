package hs.aalen.urlaub.rating;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberService;
import hs.aalen.urlaub.vacationWish.VacationWish;
import hs.aalen.urlaub.vacationWish.VacationWishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    VacationWishService vacationWishService;

    public void saveRating(Rating rating) {
        ratingRepository.save(rating);
    }

    public List<Rating> getRatingsByMemberId(Long memberId) {
        return ratingRepository.findByMemberId(memberId);
    }

    // This method can be used to get all the ratings for a specific vacation wish
    public List<Rating> getRatingsForVacationWishId(Long vacationWishId) {
        return ratingRepository.findByVacationWishId(vacationWishId);
    }

    // If a Member has already rated a vacation wish, the rating will be updated
    // Otherwise a new rating will be created
    public Rating createOrUpdateRating(Long memberId, Long vacationWishId, int score) {
        Member member = memberService.getMember(memberId);
        VacationWish vacationWish = vacationWishService.getVacationWish(vacationWishId);

        Rating existingRating = ratingRepository.findByMemberIdAndVacationWishId(memberId, vacationWishId);

        if (existingRating != null) {
            return updateRating(existingRating, score);
        }

        Rating rating = new Rating();
        rating.setMember(member);
        rating.setVacationWish(vacationWish);
        rating.setScore(score);

        return ratingRepository.save(rating);
    }

    public Rating updateRating(Rating existingRating, int newScore) {
        existingRating.setScore(newScore);
        return ratingRepository.save(existingRating);
    }

    public Double getAverageRatingForVacationWish(Long vacationWishId) {
        return ratingRepository.findAverageRatingByVacationWishId(vacationWishId);
    }

    public Rating findByMemberIdAndVacationWishId(Long memberId, Long vacationWishId) {
        return ratingRepository.findByMemberIdAndVacationWishId(memberId, vacationWishId);

    }

    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }
}
