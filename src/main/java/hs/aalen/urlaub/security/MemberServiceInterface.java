package hs.aalen.urlaub.security;

import java.util.List;

import hs.aalen.urlaub.member.Member;


public interface MemberServiceInterface {

    void saveMember(MemberDto memberDto);

    Member findMemberByEmail(String email);

    List<MemberDto> findAllMembers();
}