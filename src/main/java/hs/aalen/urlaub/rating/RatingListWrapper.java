package hs.aalen.urlaub.rating;

import java.util.ArrayList;
import java.util.List;

public class RatingListWrapper {
    private List<Rating> ratings;

    // Constructors
    public RatingListWrapper() {
        ratings = new ArrayList<>();
    }

    public RatingListWrapper(List<Rating> ratings) {
        this.ratings = ratings;
    }

    // Getters and setters
    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
