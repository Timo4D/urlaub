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

@Entity
public class VacationWish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String location;
  private String description;

  @ManyToOne
  @JoinColumn(name = "vacation_id")
  private Vacation vacation;

  //default constructor
  @ManyToMany(mappedBy = "favorite")
  @JsonIgnore //Otherwise problems with recursion in json
  private List<Member> memberFavorite;

  public VacationWish() {}

  //constructor with variables
  public VacationWish(long id, String location, String description) {
    this.id = id;
    this.location = location;
    this.description = description;
  }

  //Getters and Setters
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
}
