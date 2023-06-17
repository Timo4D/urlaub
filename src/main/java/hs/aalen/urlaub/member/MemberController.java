package hs.aalen.urlaub.member;

import hs.aalen.urlaub.vacation.Vacation;
import hs.aalen.urlaub.vacation.VacationService;
import hs.aalen.urlaub.vacationWish.VacationWishService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MemberController {
  //----connection to MemberService class------------------
  @Autowired
  MemberService memberService;

  @Autowired
  VacationWishService wishService;

  @Autowired
    private MemberRepository memberRepository;

    @Autowired
  private VacationService vacationService;

  //-------------------------------------------------------
  //-------URL mapping-------------------------------------

  @GetMapping("/api/member/{memberId}/vacations")
public List<Vacation> getVactionsForMember(@PathVariable Long memberId) {
  Member member = memberService.getMember(memberId);
  List<Vacation> memberVacations = new ArrayList<>();

  for (Vacation vacation : vacationService.getVacationList()) {
    if (vacation.getMemberAccess().contains(member)) {
      memberVacations.add(vacation);
    }
  }

  return memberVacations;
} 

  @GetMapping("/api/member")
  public List<Member> getMemberList() {
    return memberService.getMemberList();
  }

  @GetMapping("/api/member/{id}")
  public Member getMember(@PathVariable Long id) {
    return memberService.getMember(id);
  }

  @PostMapping("/api/member")
  public void addMember(@RequestBody Member member) {
    memberService.addMember(member);
  }

  @PutMapping("/api/member/{id}")
  public void updateMember(@PathVariable long id, @RequestBody Member member) {
    memberService.updateMember(id, member);
  }

  @DeleteMapping("/api/member/{id}")
  public void deleteMember(@PathVariable long id) {
    memberService.deleteMember(id);
  }
  //-----------------------------------------------------------

  //-------Routes for Thymleaf-------------------------------------
  @GetMapping("/member")
  public ModelAndView showMember() {
    ModelAndView mav = new ModelAndView("list-member");
    List<Member> list = memberService.getMemberList();
    mav.addObject("members", list);
    return mav;
  }

  @GetMapping("/addMember")
  public ModelAndView addMember() {
    ModelAndView mav = new ModelAndView("add-member-form");
    Member newMember = new Member();
    mav.addObject("wishes", wishService.getVacationWishList());
    mav.addObject("member", newMember);
    return mav;
  }
/* 
  @PostMapping("/saveMember")
  public RedirectView saveMember(@ModelAttribute Member member) {
    memberService.addMember(member);
    return new RedirectView("/member");
  }
  */

  @PostMapping("/saveMember")
  public ModelAndView saveMember(@ModelAttribute Member member) {
      Optional<Member> existingMember = memberRepository.findByEmail(member.getEmail());
      if (existingMember.isPresent()) {
          ModelAndView mav = new ModelAndView("register");
          mav.addObject("error", "Email already exists");
          mav.addObject("member", member);
          return mav;
      } else {
          memberService.addMember(member);
          return new ModelAndView(new RedirectView("/member"));
      }
  }


  @GetMapping("/updateMember")
  public ModelAndView updateMember(@RequestParam Long memberId) {
    ModelAndView mav = new ModelAndView("add-member-form");
    Member member = memberService.getMember(memberId);
    mav.addObject("member", member);
    return mav;
  }

  @GetMapping("/deleteMember")
  public RedirectView deleteMember(@RequestParam Long memberId) {
    memberService.deleteMember(memberId);
    return new RedirectView("/member");
  }
  //-----------------------------------------------------------
}
