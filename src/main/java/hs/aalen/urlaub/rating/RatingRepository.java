package hs.aalen.urlaub.rating;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends ListCrudRepository<Rating, Long> {
    // This method can be used to get all the ratings given by a member
    List<Rating> findByMemberId(Long memberId);

    // This method can be used to get all the ratings for a vacation wish
    List<Rating> findByVacationWishId(Long vacationWishId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.vacationWish.id = :vacationWishId")
    Double findAverageRatingByVacationWishId(@Param("vacationWishId")Long vacationWishId);

    Rating findByMemberIdAndVacationWishId(Long memberId, Long vacationWishId);

    // This method can be used to get all the sum of ratings for a specific vacation wish
    @Query("SELECT SUM(r.score) FROM Rating r WHERE r.vacationWish.id = :vacationWishId")
    Integer findSumOfRatingsByVacationWishId(@Param("vacationWishId") Long vacationWishId);
}
