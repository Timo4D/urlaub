package hs.aalen.urlaub.vacationWish;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.vacation.Vacation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;


@Entity
public class VacationWish {

  //-----------global declarations--------------------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; //primary key for VacationWish-class

  private String location;
  private String description;

  private int rating;

  //--------------------------------------------------------------------
  //--------entity-relation-annotation----------------------------------
  @ManyToOne
  @JoinColumn(name = "vacation_id")
  private Vacation vacation;

  @ManyToMany(mappedBy = "favorite")
  @JsonIgnore //Otherwise problems with recursion in json
  private List<Member> memberFavorite;

  //-----------------------------------------------------
  //---------constructors-----------------------------------
  //default constructor
  public VacationWish() {}

  //constructor with variables
  public VacationWish(long id, String location, String description) {
    this.id = id;
    this.location = location;
    this.description = description;
  }

  //----------------------------------------------------
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
  //---------------------------------------------------------------

  private List<VacationWish> sortWishesByRating(Long vacationId) {
    List<VacationWish> allWishes = VacationWishRepository.findByVacationId(vacationId);

    // Sortiere die Wünsche absteigend nach der Bewertung
    List<VacationWish> sortedWishes = allWishes.stream()
            .sorted(Comparator.comparing(VacationWish::getRating).reversed())
            .collect(Collectors.toList());

    return sortedWishes;
  }

  public int getRating() {
  return rating;
  }




}
