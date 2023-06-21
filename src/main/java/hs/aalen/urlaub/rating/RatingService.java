package hs.aalen.urlaub.rating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberService;
import hs.aalen.urlaub.vacationWish.VacationWish;
import hs.aalen.urlaub.vacationWish.VacationWishService;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

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

    // This method can be used to get all the ratings given by a specific member
    public List<Rating> getRatingsByMemberId(Long memberId) {
        return ratingRepository.findByMemberId(memberId);
    }

    // This method can be used to get all the ratings for a specific vacation wish
    public List<Rating> getRatingsForVacationWishId(Long vacationWishId) {
        return ratingRepository.findByVacationWishId(vacationWishId);
    }

    public boolean hasMemberRatedVacationWish(Member member, VacationWish vacationWish) {
        return ratingRepository.findByMemberIdAndVacationWishId(member.getId(), vacationWish.getId()) != null;
    }

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
