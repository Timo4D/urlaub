package hs.aalen.urlaub.security;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  //---------Methods---------------------------------
  @GetMapping
  public String showRegistrationForm() {
    return "register"; // for use in Thymeleaf
  }

  @PostMapping
  public String registerUser(@RequestBody Member member) {
    member.setPassword(passwordEncoder.encode(member.getPassword()));
    memberRepository.save(member);
    return "login"; // after user registrated successfully --> send him to login page
  }
  //----------------------------------------------------
}
