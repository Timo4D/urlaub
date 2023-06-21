package hs.aalen.urlaub.blackbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;


import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberController;
import hs.aalen.urlaub.member.MemberRepository;
import hs.aalen.urlaub.member.MemberService;
import hs.aalen.urlaub.security.SecurityController;
import hs.aalen.urlaub.vacation.Vacation;
import hs.aalen.urlaub.vacationWish.VacationWishController;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTests {

  @Autowired
  VacationWishController vacationWishController = new VacationWishController();

  @Autowired
  private MemberController memberController = new MemberController();

  @Autowired
  private SecurityController securityController = new SecurityController();

  @Autowired
  private MemberRepository memberRepository;

  private Long createdId;

  @Test //test the registration of a member
  public void testRegisterUser() {
    Member member = new Member();
    member.setName("John");
    member.setSurname("Doe");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("john.doe@example.com");
    member.setPassword("password");
    member.setRoles("ROLE_USER");

    ModelAndView mav = securityController.register();
    mav.addObject("member", member);
    ResponseEntity<String> response = registerMember(memberRepository, member);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("User registered successfully", response.getBody());

    memberController.addMember(member);
    Member savedMember = memberController.getMember(member.getId());
    createdId = savedMember.getId();
  }

  @Test //test login of a member
  public void testUserLogin() {
    Member member = new Member();
    member.setName("John");
    member.setSurname("Doe");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("john.doe@example.com");
    member.setPassword("password");
    member.setRoles("ROLE_USER");

    securityController.register();
    ModelAndView registerMav = securityController.register();
    registerMav.addObject("member", member);
    ResponseEntity<String> response = registerMember(memberRepository, member);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("User registered successfully", response.getBody());

    ModelAndView loginMav = securityController.login();

    assertEquals("login", loginMav.getViewName());

    memberController.addMember(member);
    Member savedMember = memberController.getMember(member.getId());
    createdId = savedMember.getId();
  }

  //method for registering a member
  private ResponseEntity<String> registerMember(
    MemberRepository memberRepository,
    Member member
  ) {
    Optional<Member> existingMember = memberRepository.findByEmail(
      member.getEmail()
    );
    if (existingMember.isPresent()) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("Email already exists");
    } else {
      memberRepository.save(member);
      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("User registered successfully");
    }
  }
 

 @Test // Test for login with invalid access code
public void testInvalidUserLogin() {
    String email = "invalid@example.com";
    String password = "invalidpassword";

    ModelAndView loginMav = securityController.login();
    assertEquals("login", loginMav.getViewName());

    ResponseEntity<String> response = loginUser(email, password);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid email or password", response.getBody());
}

// method for logging in a member
private ResponseEntity<String> loginUser(String email, String password) {
    Optional<Member> existingMember = memberRepository.findByEmail(email);
    if (existingMember.isPresent() && existingMember.get().getPassword().equals(password)) {
        return ResponseEntity.status(HttpStatus.OK).body("Login successful");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}


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
      "Adding 200 members took longer than expected."
    );

    verify(memberServiceMock, times(200)).addMember(any(Member.class));
  }



}
