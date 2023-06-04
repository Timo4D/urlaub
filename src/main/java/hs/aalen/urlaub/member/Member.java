package hs.aalen.urlaub.member;

import hs.aalen.urlaub.vacationWish.VacationWish;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.sql.Date; //import needed for Date-datatype
import java.util.List;

@Entity
public class Member {

  //-----------global declarations--------------------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id; //primary key for Member-class

  private String name;
  private String surname;
  private Date birthdate;
  private String email; //mail-address could be helpful/ necessary for login reasons?
  private String password; //for member login

  //--------------------------------------------------------------------

  //--------entity-relation-annotation----------------------------------
  @ManyToMany
  private List<VacationWish> favorite;

  //------------------------------------------------------------------
  //---------constructors-----------------------------------
  //default constructor
  public Member() {}

  //constructor with variables
  public Member(
    long id,
    String name,
    String surname,
    Date birthdate,
    String email,
    String password
  ) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.birthdate = birthdate;
    this.email = email;
    this.password = password;
  }

  //--------------------------------------------------
  //Relationship N-to-M --> favorite;
  public List<VacationWish> getFavoriteVacationWish() {
    return favorite;
  }

  public void addVacationWishToFavorites(VacationWish v) {
    this.favorite.add(v);
  }

  public void setVacationWishToFavorites(List<VacationWish> list) {
    this.favorite = list;
  }

  //-------------------------------------------------------

  //------------Getters and Setters--------------------
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}
  //----------------------------------------------------
}
