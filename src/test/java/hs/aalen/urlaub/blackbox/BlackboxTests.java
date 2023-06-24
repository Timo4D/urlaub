package hs.aalen.urlaub.blackbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberController;
import hs.aalen.urlaub.member.MemberRepository;
import hs.aalen.urlaub.rating.RatingController;
import hs.aalen.urlaub.security.SecurityController;
import hs.aalen.urlaub.vacation.VacationController;
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
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTests {

  @Autowired
  VacationWishController vacationWishController = new VacationWishController();

  @Autowired
  VacationController vacationController = new VacationController();

  @Autowired
  private MemberController memberController = new MemberController();

  @Autowired
  private SecurityController securityController = new SecurityController();

  @Autowired
  private RatingController ratingController = new RatingController();

  @Autowired
  private MemberRepository memberRepository;

  private Long createdId;

  @Test
  public void memberContextLoads() throws Exception {
    assertThat(memberController).isNotNull();
  }

  @Test
  public void vacationContextLoads() throws Exception {
    assertThat(vacationController).isNotNull();
  }

  @Test
  public void vacationControllerLoads() throws Exception {
    assertThat(vacationWishController).isNotNull();
  }

  @Test
  public void ratingControllerLoads() throws Exception {
    assertThat(ratingController).isNotNull();
  }

  @Test // test the registration of a member
  public void testRegisterUser() {
    Member member = new Member();
    member.setName("Test");
    member.setSurname("Tester");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("Test@test.de");
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

  @Test // test the registration of a member with Umlaut in mail address
  public void testRegisterUser2() {
    Member member = new Member();
    member.setName("Test");
    member.setSurname("Tüäöst");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("Täst.öäüe@üäö.de");
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

  @Test // test login of a member
  public void testUserLogin() {
    Member member = new Member();
    member.setName("Test");
    member.setSurname("Tester");
    member.setBirthdate(new Date(2000, 01, 01));
    member.setEmail("test.tester@test.de");
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

  // method for registering a member
  private ResponseEntity<String> registerMember(
      MemberRepository memberRepository,
      Member member) {
    Optional<Member> existingMember = memberRepository.findByEmail(
        member.getEmail());
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
    String email = "invalid@test.de";
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
    if (existingMember.isPresent() &&
        existingMember.get().getPassword().equals(password)) {
      return ResponseEntity.status(HttpStatus.OK).body("Login successful");
    } else {
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("Invalid email or password");
    }
  }
}
