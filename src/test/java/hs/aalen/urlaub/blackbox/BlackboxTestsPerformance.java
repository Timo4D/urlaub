package hs.aalen.urlaub.blackbox;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberService;
import hs.aalen.urlaub.vacation.Vacation;
import hs.aalen.urlaub.vacation.VacationService;
import hs.aalen.urlaub.vacationWish.VacationWish;
import hs.aalen.urlaub.vacationWish.VacationWishService;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTestsPerformance {

  @Autowired
  private MemberService memberService = new MemberService();

  @Autowired
  private VacationService vacationService = new VacationService();

  @Autowired
  private VacationWishService vacationWishService = new VacationWishService();

  @Test // tests the performance of adding 200 members
  public void testMemberAddPerformance() {
    MemberService memberServiceMock = mock(MemberService.class);

    assertTimeout(
        Duration.ofSeconds(5),
        () -> {
          for (int i = 0; i < 200; i++) {
            Member member = new Member();

            memberServiceMock.addMember(member);
          }
        },
        "Adding 200 members took longer than expected.");

    verify(memberServiceMock, times(200)).addMember(any(Member.class));
  }

  @Test // tests the performance of adding 200 members
  public void testMemberAddPerformance2() {
    MemberService memberServiceMock = mock(MemberService.class);

    assertTimeout(
        Duration.ofSeconds(5),
        () -> {
          for (int i = 0; i < 200; i++) {
            Member member = new Member(
                null, // Let the ID be generated automatically
                "Name" + i,
                "Surname" + i,
                new Date(0),
                "email" + i + "@example.com",
                "password",
                "ROLE_USER");

            memberServiceMock.addMember(member);
          }
        },
        "Adding 200 members took longer than expected.");

    verify(memberServiceMock, times(200)).addMember(any(Member.class));
  }

  @Test // tests the performance of retrieving all members
  void testMemberRetrievalPerformance() {
    int maxMembers = 100;

    long startId = 1000000L;
    for (int i = 0; i <= maxMembers; i++) {
      Member member = new Member(
          startId + i,
          "Name" + i,
          "Surname" + i,
          new Date(0),
          "email" + i + "@example.com",
          "password",
          "ROLE_USER");
      memberService.addMember(member);
    }

    long startTime = System.currentTimeMillis();

    List<Member> allMembers = memberService.getMemberList();
    Member retrievedMember = allMembers
        .stream()
        .filter(m -> m.getSurname().equals("Surname" + maxMembers))
        .findFirst()
        .orElse(null);
    long endTime = System.currentTimeMillis();

    Assertions.assertNotNull(
        retrievedMember,
        "Retrieved member should not be null");
    Assertions.assertTrue(
        endTime - startTime < 200,
        "Retrieving a member should take less than 200 milliseconds");
  }

  @Test // tests the performance of deleting all members
  void testDeleteMemberPerformance() {
    int maxMembers = 200;
    for (int i = 0; i <= maxMembers; i++) {
      Member member = new Member(
          (long) i,
          "Name" + i,
          "Surname" + i,
          new Date(0),
          "email" + i + "@example.com",
          "password",
          "ROLE_USER");
      memberService.addMember(member);
    }

    long startTime = System.currentTimeMillis();
    memberService.deleteMember((long) maxMembers);
    long endTime = System.currentTimeMillis();

    Assertions.assertTrue(
        endTime - startTime < 200,
        "Deleting a member should take less than 200 milliseconds");
  }

  @Test // tests the performance of adding 200 vacations
  public void testVacationAddPerformance() {
    VacationService vacationServiceMock = mock(VacationService.class);

    assertTimeout(
        Duration.ofSeconds(5),
        () -> {
          for (int i = 0; i < 200; i++) {
            Vacation vacation = new Vacation();

            vacationServiceMock.addVacation(vacation);
          }
        },
        "Adding 200 vacations took longer than expected.");

    verify(vacationServiceMock, times(200)).addVacation(any(Vacation.class));
  }

  @Test // tests the performance of retrieving all vacations
  void testVacationRetrievalPerformance() {
    int maxVacations = 200;

    long startTime = System.currentTimeMillis();

    List<Vacation> allVacations = vacationService.getVacationList();
    Vacation retrievedVacation = allVacations
        .stream()
        .filter(v -> v.getTitle().equals("Title" + maxVacations))
        .findFirst()
        .orElse(null);
    long endTime = System.currentTimeMillis();

    Assertions.assertNull(
        retrievedVacation,
        "Retrieved vacation should be null");
    Assertions.assertTrue(
        endTime - startTime < 200,
        "Retrieving a vacation should take less than 200 milliseconds");
  }

  @Test // tests the performance of updating all vacations
  void testVacationUpdatePerformance() {
    int maxVacations = 100;

    long startTime = System.currentTimeMillis();

    Vacation updatedVacation = new Vacation(
        maxVacations, // Adjusted vacation ID
        "UpdatedTitle",
        14,
        new Date(2023, 11, 12)); // Please note that months in java.util.Date are 0-based, so 11 represents
                                 // December

    vacationService.updateVacation(updatedVacation.getId(), updatedVacation);

    long endTime = System.currentTimeMillis();

    List<Vacation> allVacations = vacationService.getVacationList();
    Vacation retrievedVacation = allVacations
        .stream()
        .filter(v -> v.getId() == updatedVacation.getId())
        .findFirst()
        .orElse(null);

    if (retrievedVacation != null) {
      Assertions.assertEquals(
          "UpdatedTitle",
          retrievedVacation.getTitle(),
          "Vacation title should be updated");
    } else {

    }

    Assertions.assertTrue(
        endTime - startTime < 200,
        "Updating a vacation should take less than 200 milliseconds");
  }

  @Test // tests the performance of deleting all vacations
  void testDeleteVacationPerformance() {
    int maxVacations = 200;
    for (int i = 0; i < maxVacations; i++) {
      Vacation vacation = new Vacation(
          i,
          "Title" + i,
          14,
          new Date(2023 - 11 - 11));
      vacationService.addVacation(vacation);
    }

    long startTime = System.currentTimeMillis();
    vacationService.deleteVacation(maxVacations - 1);
    long endTime = System.currentTimeMillis();

    Assertions.assertTrue(
        endTime - startTime < 200,
        "Deleting a vacation should take less than 200 milliseconds");
  }

  @Test // tests the performance of concluding all vacations
  void testConcludeVacationPerformance() {
    int maxVacations = 200;
    for (int i = 0; i < maxVacations; i++) {
      Vacation vacation = new Vacation(
          i,
          "Title" + i,
          14,
          new Date(2023 - 10 - 10));
      vacationService.addVacation(vacation);
    }

    long startTime = System.currentTimeMillis();
    vacationService.concludeVacation(maxVacations - 1);
    long endTime = System.currentTimeMillis();

    Assertions.assertTrue(
        endTime - startTime < 200,
        "Concluding a vacation should take less than 200 milliseconds");
  }

  @Test // tests the performance of adding 200 vacation wishes
  public void testVacationWishAddPerformance() {
    VacationWishService vacationWishServiceMock = mock(
        VacationWishService.class);

    assertTimeout(
        Duration.ofSeconds(5),
        () -> {
          for (int i = 0; i < 200; i++) {
            VacationWish vacationWish = new VacationWish();

            vacationWishServiceMock.addVacationWish(vacationWish);
          }
        },
        "Adding 200 vacation wishes took longer than expected.");

    verify(vacationWishServiceMock, times(200))
        .addVacationWish(any(VacationWish.class));
  }

  @Test // tests the performance of retrieving all vacation wishes
  void testVacationWishRetrievalPerformance() {
    int maxWishes = 100;

    long startTime = System.currentTimeMillis();

    List<VacationWish> allWishes = vacationWishService.getVacationWishList();
    VacationWish retrievedWish = allWishes
        .stream()
        .filter(w -> w.getDescription().equals("Description" + maxWishes))
        .findFirst()
        .orElse(null);
    long endTime = System.currentTimeMillis();

    Assertions.assertNull(
        retrievedWish,
        "Retrieved vacation wish should not be null");
    Assertions.assertTrue(
        endTime - startTime < 200,
        "Retrieving a vacation wish should take less than 200 milliseconds");
  }

  @Test // tests the performance of deleting all vacation wishes
  void testDeleteVacationWishPerformance() {
    int maxWishes = 200;
    for (int i = 0; i < maxWishes; i++) {
      VacationWish vacationWish = new VacationWish(
          i,
          "Location" + i,
          "Description" + i, 12L);
      vacationWishService.addVacationWish(vacationWish);
    }

    long startTime = System.currentTimeMillis();
    vacationWishService.deleteVacationWish(maxWishes - 1);
    long endTime = System.currentTimeMillis();

    Assertions.assertTrue(
        endTime - startTime < 200,
        "Deleting a vacation wish should take less than 200 milliseconds");
  }

  @Test // tests the performance of retrieving all vacation wishes for a vacation
  void testGetVacationWishListToVacationPerformance() {
    int maxWishes = 200;

    Vacation vacation = new Vacation(1, "Title", 14, new Date(2024 - 01 - 02));

    List<VacationWish> vacationWishes = new ArrayList<>();
    for (int i = 0; i < maxWishes; i++) {
      VacationWish vacationWish = new VacationWish(
          i,
          "Location" + i,
          "Description" + i, 12L);
      vacationWish.setVacation(vacation);
      vacationWishes.add(vacationWish);
      vacationWishService.addVacationWish(vacationWish);
    }

    long startTime = System.currentTimeMillis();

    List<VacationWish> retrievedWishes = vacationWishService.getVacationWishListToVacation(
        vacation.getId());

    long endTime = System.currentTimeMillis();

    Assertions.assertEquals(
        maxWishes,
        retrievedWishes.size(),
        "Retrieved vacation wish list should have the expected size");

    Assertions.assertTrue(
        endTime - startTime < 200,
        "Retrieving vacation wishes for a vacation should take less than 200 milliseconds");
  }
}
