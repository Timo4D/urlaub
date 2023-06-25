package hs.aalen.urlaub.security;

import hs.aalen.urlaub.member.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SecurityController {

    @GetMapping("/page/login")
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

}
