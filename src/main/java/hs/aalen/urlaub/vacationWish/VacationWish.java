package hs.aalen.urlaub.vacationWish;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hs.aalen.urlaub.rating.Rating;
import hs.aalen.urlaub.vacation.Vacation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class VacationWish {

    //-----------global declarations--------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //primary key for VacationWish-class

    private String location;
    private String description;

    //--------entity-relation-annotation----------------------------------
    @ManyToOne
    @JoinColumn(name = "vacation_id")
    @JsonIgnore
    private Vacation vacation;

    @OneToMany(mappedBy = "vacationWish")
    private List<Rating> ratings;

    //---------constructors-----------------------------------
    public VacationWish() {
    }

    public VacationWish(long id, String location, String description) {
        this.id = id;
        this.location = location;
        this.description = description;
    }

    //--------------Getters and Setters----------------------------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }
}
