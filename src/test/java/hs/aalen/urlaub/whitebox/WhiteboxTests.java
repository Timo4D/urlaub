package hs.aalen.urlaub.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberController;
import hs.aalen.urlaub.vacation.Vacation;
import hs.aalen.urlaub.vacation.VacationController;
import hs.aalen.urlaub.vacationWish.VacationWish;
import hs.aalen.urlaub.vacationWish.VacationWishController;
import java.sql.Date;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback

public class WhiteboxTests {

  @Autowired
  private MemberController memberController = new MemberController();

  @Autowired
  VacationWishController vacationWishController = new VacationWishController();

  @Autowired
  private VacationController vacationController = new VacationController();

  private Long createdMemberId;
  private Long createdVacationWishId;
  private Long createdVacationId;

  @Test //test the creation of a member
  public void testAddMember() {
    Member member = new Member();
    member.setName("Herr");
    member.setSurname("Test");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("Test@test.de");
    member.setPassword("password");
    member.setRoles("ROLE_USER");

    memberController.addMember(member);

    Member savedMember = memberController.getMember(member.getId());
    assertNotNull(savedMember);
    assertEquals("Herr", savedMember.getName());
    assertEquals("Test", savedMember.getSurname());
    assertEquals(new Date(2000, 01, 01), savedMember.getBirthdate());
    assertEquals("Test@test.de", savedMember.getEmail());
    assertEquals("password", savedMember.getPassword());
    assertEquals("ROLE_USER", savedMember.getRoles());

    createdMemberId = savedMember.getId();
  }

  @Test //test the update of a member
  public void testUpdateMember() {
    Member member = new Member();
    member.setName("Herr");
    member.setSurname("Test");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("Test@test.de");
    member.setPassword("password");
    member.setRoles("ROLE_USER");

    memberController.addMember(member);

    member.setName("Frau");
    member.setSurname("Test");
    member.setEmail("Test@test.de");

    memberController.updateMember(member.getId(), member);

    Member updatedMember = memberController.getMember(member.getId());
    assertNotNull(updatedMember);
    assertEquals("Frau", updatedMember.getName());
    assertEquals("Test", updatedMember.getSurname());
    assertEquals("Test@test.de", updatedMember.getEmail());

    createdMemberId = updatedMember.getId();
  }

  @Test //test the deletion of a member
  public void testDeleteMember() {
    Member member = new Member();
    member.setName("Herr");
    member.setSurname("Test");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("Test@test.de");
    member.setPassword("password");
    member.setRoles("ROLE_USER");

    memberController.addMember(member);

    memberController.deleteMember(member.getId());

    Member deletedMember = memberController.getMember(member.getId());
    assertNull(deletedMember);
  }

  @Test //test the creation of a vacationWish
  public void testAddVacationWish() {
    VacationWish vacationWish = new VacationWish();
    vacationWish.setLocation("Beach");
    vacationWish.setDescription("Relaxing beach vacation");

    vacationWishController.addVacationWish(vacationWish);

    VacationWish savedWish = vacationWishController.getVacationWish(
      vacationWish.getId()
    );
    assertNotNull(savedWish);
    assertEquals("Beach", savedWish.getLocation());
    assertEquals("Relaxing beach vacation", savedWish.getDescription());

    createdVacationWishId = savedWish.getId();
  }

  @Test //test the update of a vacationWish
  public void testUpdateVacationWish() {
    VacationWish vacationWish = new VacationWish();
    vacationWish.setLocation("Beach");
    vacationWish.setDescription("Relaxing beach vacation");

    vacationWishController.addVacationWish(vacationWish);

    vacationWish.setLocation("Mountain");
    vacationWish.setDescription("Hiking mountain vacation");

    vacationWishController.updateVacationWish(
      vacationWish.getId(),
      vacationWish
    );

    VacationWish updatedWish = vacationWishController.getVacationWish(
      vacationWish.getId()
    );
    assertNotNull(updatedWish);
    assertEquals("Mountain", updatedWish.getLocation());
    assertEquals("Hiking mountain vacation", updatedWish.getDescription());

    createdVacationWishId = updatedWish.getId();
  }

  @Test //test the deletion of a vacationWish
  public void testDeleteVacationWish() {
    VacationWish vacationWish = new VacationWish();
    vacationWish.setLocation("Beach");
    vacationWish.setDescription("Relaxing beach vacation");

    vacationWishController.addVacationWish(vacationWish);

    vacationWishController.deleteVacationWish(vacationWish.getId());

    VacationWish deletedWish = vacationWishController.getVacationWish(
      vacationWish.getId()
    );
    assertNull(deletedWish);
  }

  @Test // Test the creation of a vacation
  public void testAddVacation() {
    Vacation vacation = new Vacation();
    vacation.setTitle("Summer Vacation");
    vacation.setTimePeriod(14);
    vacation.setStartDate(new Date(2023, 6, 1));
  

    vacationController.addVacation(vacation);

    Vacation savedVacation = vacationController.getVacation(vacation.getId());
    assertNotNull(savedVacation);
    assertEquals("Summer Vacation", savedVacation.getTitle());
    assertEquals(14, savedVacation.getTimePeriod());
    assertEquals(new Date(2023, 6, 1), savedVacation.getStartDate());

    createdVacationId = savedVacation.getId();
  }

  @Test // Test the update of a vacation
  public void testUpdateVacation() {
    Vacation vacation = new Vacation();
    vacation.setTitle("Winter Vacation");
    vacation.setTimePeriod(7);
    vacation.setStartDate(new Date(2023, 12, 24));

    vacationController.addVacation(vacation);

    Vacation savedVacation = vacationController.getVacation(vacation.getId());
    assertNotNull(savedVacation);

    savedVacation.setTitle("New Year Vacation");
    savedVacation.setTimePeriod(5);
    savedVacation.setStartDate(new Date(2023, 12, 30));

    vacationController.updateVacation(savedVacation.getId(), savedVacation);

    Vacation updatedVacation = vacationController.getVacation(
      savedVacation.getId()
    );
    assertNotNull(updatedVacation);
    assertEquals("New Year Vacation", updatedVacation.getTitle());
    assertEquals(5, updatedVacation.getTimePeriod());
    assertEquals(new Date(2023, 12, 30), updatedVacation.getStartDate());

    createdVacationId = updatedVacation.getId();
  }

  @Test // Test the deletion of a vacation
  public void testDeleteVacation() {
    Vacation vacation = new Vacation();
    vacation.setTitle("Beach Vacation");
    vacation.setTimePeriod(7);
    vacation.setStartDate(new Date(2023, 8, 1));

    vacationController.addVacation(vacation);

    Vacation savedVacation = vacationController.getVacation(vacation.getId());
    assertNotNull(savedVacation);

    vacationController.deleteVacation(savedVacation.getId());

    Vacation deletedVacation = vacationController.getVacation(
      savedVacation.getId()
    );
    assertEquals(null, deletedVacation);
  }

  @AfterEach //delete created member/vacation/vacationWish after test
  public void cleanup() {
    if (createdMemberId != null) {
      memberController.deleteMember(createdMemberId);
    }
    if (createdVacationWishId != null) {
      vacationWishController.deleteVacationWish(createdVacationWishId);
    }
    if (createdVacationId != null) {
      vacationController.deleteVacation(createdVacationId);
    }
  }
}
