package com.example.jpabook.service;

import com.example.jpabook.domain.Member;
import com.example.jpabook.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Access;
import javax.swing.*;

import static org.junit.Assert.*;
//테스트시 스프링과 엮어서실행
@RunWith(SpringRunner.class)
//스프링부트컨테이너안에서 실행
@SpringBootTest
//테스트케이스에선 기본이 롤백.
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void hyunwookTest() throws  Exception{
        Member member = new Member();
        member.setName("lee");
        memberRepository.save(member);
        member = memberRepository.findOne(member.getId());
        System.out.println("test>>>>>>>>>>>>>>>>"+member.getId());
    }
    /*
    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception{

        Member member = new Member();
        member.setName("Lee");

        Long saveId = memberService.join(member);


        assertEquals(member, memberService.findOne(saveId));
    }

    @Test(expected =  IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        memberService.join(member1);
        memberService.join(member2);


        fail("예외가 발생해야함");
    }
     */
}