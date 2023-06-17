package hs.aalen.urlaub.rating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.vacationWish.VacationWish;

@Service
public class RatingService {

  @Autowired
  RatingRepository ratingRepository;



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

  public void addRating(Rating rating) {
    if (rating.getScore() < 1 || rating.getScore() > 10) {
      throw new IllegalArgumentException("Score must be between 1 and 10");
    }
    if (hasMemberRatedVacationWish(rating.getMember(), rating.getVacationWish())) {
      throw new IllegalStateException("Member has already rated this vacation wish");
    }
    ratingRepository.save(rating);
  }

  public Double getAverageRatingForVacationWish(Long vacationWishId) {
    return ratingRepository.findAverageRatingByVacationWishId(vacationWishId);
  }

   public Rating findByMemberIdAndVacationWishId(Long memberId, Long vacationWishId) {
        return ratingRepository.findByMemberIdAndVacationWishId(memberId, vacationWishId)
            .orElseThrow(() -> new IllegalArgumentException("No rating found for the given member and vacation wish IDs"));
    }
    
  }
