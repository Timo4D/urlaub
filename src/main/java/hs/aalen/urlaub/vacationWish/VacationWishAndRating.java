package hs.aalen.urlaub.vacationWish;


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
