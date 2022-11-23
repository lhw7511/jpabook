package com.example.jpabook.service;

import com.example.jpabook.domain.Member;
import com.example.jpabook.repository.MemberRepository;
import com.example.jpabook.repository.datajpa.MemberDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {



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
}
