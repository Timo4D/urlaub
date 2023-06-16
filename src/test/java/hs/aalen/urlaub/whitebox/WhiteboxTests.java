package hs.aalen.urlaub.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberController;
import hs.aalen.urlaub.vacationWish.VacationWish;
import hs.aalen.urlaub.vacationWish.VacationWishController;

import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WhiteboxTests {

  @Autowired
  private MemberController memberController = new MemberController();

  @Autowired VacationWishController vacationWishController = new VacationWishController();

  private Long createdMemberId;
  private Long createdVacationWishId;

  @Test //test the creation of a member
  public void testAddMember() {
    Member member = new Member();
    member.setName("John");
    member.setSurname("Doe");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("john.doe@example.com");
    member.setPassword("password");
    member.setRoles("ROLE_USER");

    memberController.addMember(member);

    Member savedMember = memberController.getMember(member.getId());
    assertNotNull(savedMember);
    assertEquals("John", savedMember.getName());
    assertEquals("Doe", savedMember.getSurname());
    assertEquals(new Date(2000, 01, 01), savedMember.getBirthdate());
    assertEquals("john.doe@example.com", savedMember.getEmail());
    assertEquals("password", savedMember.getPassword());
    assertEquals("ROLE_USER", savedMember.getRoles());

    createdMemberId = savedMember.getId();
  }

  @Test //test the update of a member
  public void testUpdateMember() {
    Member member = new Member();
    member.setName("John");
    member.setSurname("Doe");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("john.doe@example.com");
    member.setPassword("password");
    member.setRoles("ROLE_USER");

    memberController.addMember(member);

    member.setName("Jane");
    member.setSurname("Smith");
    member.setEmail("jane.smith@example.com");

    memberController.updateMember(member.getId(), member);

    Member updatedMember = memberController.getMember(member.getId());
    assertNotNull(updatedMember);
    assertEquals("Jane", updatedMember.getName());
    assertEquals("Smith", updatedMember.getSurname());
    assertEquals("jane.smith@example.com", updatedMember.getEmail());

    createdMemberId = updatedMember.getId();
  }

  @Test //test the deletion of a member
  public void testDeleteMember() {
    Member member = new Member();
    member.setName("John");
    member.setSurname("Doe");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("john.doe@example.com");
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

    VacationWish savedWish = vacationWishController.getVacationWish(vacationWish.getId());
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
    vacationWish.setDescription("Adventurous mountain vacation");

    vacationWishController.updateVacationWish(vacationWish.getId(), vacationWish);

    VacationWish updatedWish = vacationWishController.getVacationWish(vacationWish.getId());
    assertNotNull(updatedWish);
    assertEquals("Mountain", updatedWish.getLocation());
    assertEquals("Adventurous mountain vacation", updatedWish.getDescription());

    createdVacationWishId = updatedWish.getId();
}

@Test //test the deletion of a vacationWish
public void testDeleteVacationWish() {
    VacationWish vacationWish = new VacationWish();
    vacationWish.setLocation("Beach");
    vacationWish.setDescription("Relaxing beach vacation");

    vacationWishController.addVacationWish(vacationWish);

    vacationWishController.deleteVacationWish(vacationWish.getId());

    VacationWish deletedWish = vacationWishController.getVacationWish(vacationWish.getId());
    assertNull(deletedWish);
}



  @AfterEach //delete created member after test
  public void cleanup() {
    if (createdMemberId != null) {
      memberController.deleteMember(createdMemberId);
    }
    if (createdVacationWishId != null) {
      vacationWishController.deleteVacationWish(createdVacationWishId);
    }
  }
}
