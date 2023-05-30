package hs.aalen.urlaub.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//import needed for Date-datatype
import java.sql.Date;

@Entity
public class User {

  @Id
  private long id;

  private String name;
  private String surname;
  private Date birthdate;
  //mail-address could be helpful/ necessary for login reasons?
  private String email;

  //default constructor
  public User() {}

  //constructor with variables
  public User(
    long id,
    String name,
    String surname,
    Date birthdate,
    String email
  ) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.birthdate = birthdate;
    this.email = email;
  }

  //Getters and Setters
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
}
