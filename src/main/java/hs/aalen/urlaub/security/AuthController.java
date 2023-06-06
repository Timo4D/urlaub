package hs.aalen.urlaub.security;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hs.aalen.urlaub.member.Member;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class AuthController {

    private MemberServiceInterface memberServiceInterface;

    public AuthController(MemberServiceInterface memberServiceInterface) {
        this.memberServiceInterface = memberServiceInterface;
    }

    // handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        MemberDto member = new MemberDto();
        model.addAttribute("member", member);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("member") MemberDto memberDto,
                               BindingResult result,
                               Model model) {
        Member existingMember = memberServiceInterface.findMemberByEmail(memberDto.getEmail());

        if (existingMember != null && existingMember.getEmail() != null && !existingMember.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("member", memberDto);
            return "/register";
        }

        memberServiceInterface.saveMember(memberDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/members")
    public String members(Model model) {
        List<MemberDto> members = memberServiceInterface.findAllMembers();
        model.addAttribute("members", members);
        return "members";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}