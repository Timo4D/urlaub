package hs.aalen.urlaub.member;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long>{
    Member findByEmail(String email);
}
