package com.example.jpabook.service;

import com.example.jpabook.auth.MyMemberDetail;
import com.example.jpabook.domain.Member;
import com.example.jpabook.dto.MemberDto;
import com.example.jpabook.repository.MemberRepository;
import com.example.jpabook.repository.datajpa.MemberDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {



    private final MemberDataJpaRepository memberDataJpaRepository;
    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberDataJpaRepository.save(member);
        return member.getId();
    }

    //중복체크
    private void validateDuplicateMember(Member member) {
        List<Member> members = memberDataJpaRepository.findByName(member.getName());
        if(!members.isEmpty()){
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회

    @Transactional
    public List<Member> findMembers(){
        return memberDataJpaRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberDataJpaRepository.findById(memberId).get();

    }
    @Transactional
    public void update(Long id, String name){
        Member member = memberDataJpaRepository.findById(id).get();
        member.setName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberDataJpaRepository.findByEmail(email);


        return Optional.ofNullable(member).map(MyMemberDetail::new).orElseThrow(()->new UsernameNotFoundException(email));
    }

    @Transactional
    public void dirtyCheckingTest(Member memberOne){
        memberOne.setName("updateName");
    }


}
