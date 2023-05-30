package hs.aalen.urlaub.member;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  //----connection to MemberService class------------------
  @Autowired
  MemberService memberService;

  //-------------------------------------------------------
  //-------URL mapping-------------------------------------
  @GetMapping("/member")
  public List<Member> getMemberList() {
    return memberService.getMemberList();
  }

  @GetMapping("/member/{id}")
  public Member getMember(@PathVariable long id) {
    return memberService.getMember(id);
  }

  @PostMapping("/member")
  public void addMember(@RequestBody Member member) {
    memberService.addMember(member);
  }

  @PutMapping("/member/{id}")
  public void updateMember(@PathVariable long id, @RequestBody Member member) {
    memberService.updateMember(id, member);
  }

  @DeleteMapping("/member/{id}")
  public void deleteMember(@PathVariable long id) {
    memberService.deleteMember(id);
  }
  //-----------------------------------------------------------
}
