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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //primary key for Member-class

  private String name;
  private String surname;
  private Date birthdate;
  private String email; //mail-address could be helpful/ necessary for login reasons?
  private String password; //for member login
  private String roles;

  //--------------------------------------------------------------------

  //--------entity-relation-annotation----------------------------------
  

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
    String roles
  ) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.birthdate = birthdate;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  //--------------------------------------------------
  //Relationship N-to-M --> favorite;
 

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

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "Member{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", birthdate=" + birthdate +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", roles='" + roles + '\'' +
            '}';
  }

  //----------------------------------------------------
}
