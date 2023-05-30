package hs.aalen.urlaub.vacation;

import hs.aalen.urlaub.vacationWish.VacationWish;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
//import needed for Date-datatype
import java.sql.Date;
import java.util.List;

@Entity
public class Vacation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String title;
  private int timePeriod; //time period in days; for example 14 days
  //maybe useful
  private Date startDate;
  private Date endDate;

  //default constructor

  @OneToMany(mappedBy = "vacation")
  private List<VacationWish> vacationWishes;

  public Vacation() {}

  //constructor with variables
  public Vacation(
    long id,
    String title,
    int timePeriod,
    Date startDate,
    Date endDate
  ) {
    this.id = id;
    this.title = title;
    this.timePeriod = timePeriod;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  //Getters and Setters
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getTimePeriod() {
    return timePeriod;
  }

  public void setTimePeriod(int timePeriod) {
    this.timePeriod = timePeriod;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
