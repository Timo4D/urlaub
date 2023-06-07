package hs.aalen.urlaub.member;


import hs.aalen.urlaub.vacationWish.VacationWish;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import java.sql.Date; //import needed for Date-datatype
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Member {

  //-----------global declarations--------------------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //primary key for Member-class

  private String name;
  private String surname;
  private Date birthdate;
  private String email; //mail-address could be helpful/ necessary for login reasons?
  private String password; //for member login
  private String username;

  
  

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
    Long id,
    String name,
    String surname,
    Date birthdate,
    String email,
    String password,
    String username
  
  ) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.birthdate = birthdate;
    this.email = email;
    this.password = password;
    this.username = username;
    
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
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

 

  public void setFavorite(List<VacationWish> favorite) {
    this.favorite = favorite;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  
  //----------------------------------------------------
}
