package hs.aalen.urlaub.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    memberRepository.save(member);
  }

  public void updateMember(long id, Member member) {
    memberRepository.save(member);
  }

  public void deleteMember(long id) {
    memberRepository.deleteById(id);
  }
}
