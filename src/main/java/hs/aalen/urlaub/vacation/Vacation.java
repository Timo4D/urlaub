package hs.aalen.urlaub.vacation;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.vacationWish.VacationWish;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.sql.Date; //import needed for Date-datatype
import java.util.ArrayList;
import java.util.List;

@Entity
public class Vacation {

  //-----------global declarations--------------------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; //primary key for Vacation-class

  private String title;
  private int timePeriod; //time period in days; for example 14 days
  private Date startDate; //maybe useful
  private Date endDate; //maybe useful

  private boolean isActive;

 @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VacationWish> wishes = new ArrayList<>();

@OneToMany(cascade = CascadeType.ALL)
  private List<Member> memberAccess = new ArrayList<>();

  //--------------------------------------------------------------------
  //--------entity-relation-annotation----------------------------------
  @OneToMany(mappedBy = "vacation")
  private List<VacationWish> vacationWishes;

  //-----------------------------------------------------
  //---------constructors-----------------------------------
  //default constructor
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

  //-----------------------------------------------------------
  //----------Getters and Setters------------------------------
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

 public List<Member> getMemberAccess() {
    return memberAccess;
  }

  public void setMemberAccess(List<Member> memberAccess) {
    this.memberAccess = memberAccess;
  }

  public void addMemberAccess(Member access) {
    this.memberAccess.add(access);
  }

  public void removeMemberAccess(Member access) {
    this.memberAccess.remove(access);
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }
  //--------------------------------------------------------
}
