package hs.aalen.urlaub.vacationWish;


//This class is used to display the average rating of a vacation wish
//It exists to overcome an error that occurs when trying to receive Ratings from the frontend
public class VacationWishAndRating extends VacationWish {
    private Double averageRating;

    public VacationWishAndRating() {
        super();
    }

    public VacationWishAndRating(VacationWish vacationWish, Double averageRating) {
        super(vacationWish.getId(), vacationWish.getLocation(), vacationWish.getDescription());
        this.averageRating = averageRating;
    }

    public Double getAverageRating() {
        return averageRating;
    }
}
