package hs.aalen.urlaub.vacationWish;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class VacationWish {

  @Id
  private long id;

  private String location;
  private String description;

  //default constructor
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
