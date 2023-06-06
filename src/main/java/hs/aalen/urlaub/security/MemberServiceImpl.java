package hs.aalen.urlaub.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.member.MemberRepository;


@Service
public class MemberServiceImpl implements MemberServiceInterface {


    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public MemberServiceImpl(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveMember(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName() + " " + memberDto.getSurname());
        member.setEmail(memberDto.getEmail());
        // encrypt the password using spring security
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        member.setRoles(Arrays.asList(role));
        memberRepository.save(member);

    }


    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<MemberDto> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map((member) -> mapToMemberDto(member))
                .collect(Collectors.toList());
    }

    private MemberDto mapToMemberDto(Member member) {
        MemberDto memberDto = new MemberDto();
        String[] str = member.getName().split(" ");
        memberDto.setName(str[0]);
        memberDto.setSurname(str[1]);
        memberDto.setEmail(member.getEmail());
        return memberDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }


}
