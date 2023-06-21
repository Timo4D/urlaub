package hs.aalen.urlaub.rating;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface RatingRepository extends ListCrudRepository<Rating, Long> {
    // This method can be used to get all the ratings given by a member
    List<Rating> findByMemberId(Long memberId);

    // This method can be used to get all the ratings for a vacation wish
    List<Rating> findByVacationWishId(Long vacationWishId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.vacationWish.id = :vacationWishId")
    Double findAverageRatingByVacationWishId(Long vacationWishId);

    Rating findByMemberIdAndVacationWishId(Long memberId, Long vacationWishId);
}
