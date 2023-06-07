package hs.aalen.urlaub.security;




import hs.aalen.urlaub.member.MemberRepository;
import hs.aalen.urlaub.member.SecurityMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository
                .findByEmail(username)
                .map(SecurityMember::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}