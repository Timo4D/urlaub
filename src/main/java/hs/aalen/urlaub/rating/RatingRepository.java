package hs.aalen.urlaub.rating;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    // This method can be used to get all the ratings given by a  member
    List<Rating> findByMemberId(Long memberId);

    // This method can be used to get all the ratings for a vacation wish
    List<Rating> findByVacationWishId(Long vacationWishId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.vacationWish.id = :vacationWishId")
    Double findAverageRatingByVacationWishId(Long vacationWishId);

     Optional<Rating> findByMemberIdAndVacationWishId(Long memberId, Long vacationWishId);
}
