package hs.aalen.urlaub.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberController;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WhiteboxTests {

  @Autowired
  private MemberController memberController = new MemberController();

  private Long createdMemberId;

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

  @AfterEach //delete created member after test
  public void cleanup() {
    // Lösche den erstellten Member nach dem Test
    if (createdMemberId != null) {
      memberController.deleteMember(createdMemberId);
    }
  }
}
