package hs.aalen.urlaub.blackbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberController;
import hs.aalen.urlaub.member.MemberRepository;
import hs.aalen.urlaub.security.SecurityController;
import hs.aalen.urlaub.vacationWish.VacationWishController;
import java.sql.Date;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
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

  @Test //test the registration of a user/member
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

  @Test //test login of a user/member
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

  @AfterEach //delete created member/user after test
  public void cleanup() {
    if (createdId != null) {
      memberController.deleteMember(createdId);
    }
  }
}
