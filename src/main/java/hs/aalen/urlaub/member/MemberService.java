package hs.aalen.urlaub.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemberService {

  //----connection to MemberRepository class------------------
  @Autowired
  private MemberRepository memberRepository;

  //----------------------------------------------------------
  public List<Member> getMemberList() {
    ArrayList<Member> memberList = new ArrayList<>();
    Iterator<Member> iterator = memberRepository.findAll().iterator();
    while (iterator.hasNext()) memberList.add(iterator.next());
    return memberList;
  }

  public Member getMember(long id) {
    return memberRepository.findById(id).orElse(null);
  }

  public void addMember(Member member) {
    member.setRoles("ROLE_USER");
    memberRepository.save(member);
  }

  public void updateMember(long id, Member member) {
    Member existingMember = memberRepository.findById(id).orElse(null);

    if (existingMember != null) {
      if (member.getName() != null) {
        existingMember.setName(member.getName());
      }
      if (member.getSurname() != null) {
        existingMember.setSurname(member.getSurname());
      }
      if (member.getBirthdate() != null) {
        existingMember.setBirthdate(member.getBirthdate());
      }
      if (member.getEmail() != null) {
        existingMember.setEmail(member.getEmail());
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Could not find Member with id: " + id
      );
    }

    memberRepository.save(existingMember);
  }

  public void deleteMember(long id) {
    memberRepository.deleteById(id);
  }
}
