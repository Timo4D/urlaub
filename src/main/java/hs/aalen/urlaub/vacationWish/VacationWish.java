package hs.aalen.urlaub.vacationWish;

public class VacationWish {

  private String location;
  private String description;

  //default constructor
  public VacationWish() {}

  //constructor with variables
  public VacationWish(String location, String description) {
    this.location = location;
    this.description = description;
  }

  //Getters and Setters
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
