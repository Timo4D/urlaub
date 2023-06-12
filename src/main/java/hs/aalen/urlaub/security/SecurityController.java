package hs.aalen.urlaub.security;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SecurityController {

    @Autowired
    private MemberRepository memberRepository;

    

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("register");
        Member newMember = new Member();
        mav.addObject("member", newMember);
        return mav;
    }
/* 
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Member newMember) {
        Optional<Member> existingMember = memberRepository.findByEmail(newMember.getEmail());
        if (existingMember.isPresent()) {
            // Hier können Sie eine entsprechende Fehlermeldung zurückgeben
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } else {
            // Hier können Sie die Daten des neuen Mitglieds speichern
            memberRepository.save(newMember);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }
        
    }*/
}
