package hs.aalen.urlaub.blackbox;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberService;
import hs.aalen.urlaub.vacation.Vacation;
import hs.aalen.urlaub.vacation.VacationService;
import jakarta.transaction.Transactional;
import java.sql.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTestsBoundaryValueAnalysis {

  private Member member;

  private Vacation vacation;

  @Autowired
  private MemberService memberService;

  @Autowired
  private VacationService vacationService;

  @BeforeEach
  public void setup() {
    member = new Member();
    vacation = new Vacation();
  }

  @Test // Test adding a member with no input
  void testAddMemberWithEmptyInput() {
    member.setName("");
    member.setSurname("");
    member.setBirthdate(null);
    member.setEmail("");
    member.setPassword("");
    member.setRoles("");

    Assertions.assertDoesNotThrow(() -> {
      memberService.addMember(member);
    });

    Member addedMember = memberService.getMember(member.getId());
    Assertions.assertNotNull(addedMember, "Member should not be null");
  }

  @Test // Test adding a member with extremely small input
  void testAddMemberWithMinimumLength() {
    member.setName("A");
    member.setSurname("A");
    member.setBirthdate(Date.valueOf("0001-01-01"));
    member.setEmail("a@a.de");
    member.setPassword("a");
    member.setRoles("A");

    Assertions.assertDoesNotThrow(() -> {
      memberService.addMember(member);
    });

    Member addedMember = memberService.getMember(member.getId());
    Assertions.assertNotNull(addedMember, "Member should not be null");
  }

  // Test adding a member with extremely large input
  @Test
  void testAddMemberWithImmenseLength() {
    String longString = new String(new char[10000000]).replace("\0", "a");

    member.setName(longString);
    member.setSurname(longString);
    member.setBirthdate(Date.valueOf("9999-01-01"));
    member.setEmail(longString);
    member.setPassword(longString);
    member.setRoles(longString);

    Assertions.assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        memberService.addMember(member);
      }
    );
  }

  @Test // Test adding a vacation with no input
  void testAddVacationWithEmptyInput() {
    vacation.setTitle("");
    vacation.setTimePeriod(0);
    vacation.setStartDate(null);
    vacation.setIsActive(false);

    Assertions.assertDoesNotThrow(() -> {
      vacationService.addVacation(vacation);
    });

    Vacation addedVacation = vacationService.getVacation(vacation.getId());
    Assertions.assertNotNull(addedVacation, "Vacation should not be null");
  }

  @Test // Test adding a vacation with extremely small input
  void testAddVacationWithMinimumLength() {
    vacation.setTitle("A");
    vacation.setTimePeriod(1);
    vacation.setStartDate(Date.valueOf("0001-01-01"));
    vacation.setIsActive(true);

    Assertions.assertDoesNotThrow(() -> {
      vacationService.addVacation(vacation);
    });

    Vacation addedVacation = vacationService.getVacation(vacation.getId());
    Assertions.assertNotNull(addedVacation, "Vacation should not be null");
  }

  // Test adding a vacation with extremely large input
  @Test
  void testAddVacationWithImmenseLength() {
    String longString = new String(new char[10000000]).replace("\0", "a");

    vacation.setTitle(longString);
    vacation.setTimePeriod(100);
    vacation.setStartDate(Date.valueOf("9999-01-01"));
    vacation.setIsActive(true);

    Assertions.assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        vacationService.addVacation(vacation);
      }
    );
  }
}
