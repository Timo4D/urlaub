package hs.aalen.urlaub.vacationWish;

import java.util.List;

import hs.aalen.urlaub.rating.Rating;
import hs.aalen.urlaub.vacation.Vacation;
import jakarta.persistence.*;

@Entity
public class VacationWish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //primary key for VacationWish-class

    private String location;
    private String description;
    private Long authorId;

    //--------entity-relation-annotation----------------------------------
    @ManyToOne
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    @OneToMany(mappedBy = "vacationWish", cascade = CascadeType.ALL)
    private List<Rating> ratings;



    public VacationWish() {
    }

    public VacationWish(long id, String location, String description, Long authorId) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.authorId = authorId;
    }

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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
