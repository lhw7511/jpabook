package com.example.jpabook.controller;

import com.example.jpabook.domain.Address;
import com.example.jpabook.domain.Member;
import com.example.jpabook.domain.MemberRole;
import com.example.jpabook.dto.MemberDto;
import com.example.jpabook.repository.MemberRepository;
import com.example.jpabook.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {



    private final MemberService memberService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberRepository memberRepository;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        MemberRole memberRole = MemberRole.USER;
        if("ADMIN".equals(form.getMemberRole())){
            memberRole = MemberRole.ADMIN;
        }
        Member member = Member.builder()
                                .name(form.getName())
                                .address(address)
                                .email(form.getEmail())
                                .pw(bCryptPasswordEncoder.encode(form.getPw()))
                                .memberRole(memberRole)
                                        .build();




        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

    @GetMapping("/memberTest")
    public void memberTest(Long memberId){
        memberRepository.testMember(memberId);
    }
    
    





}
