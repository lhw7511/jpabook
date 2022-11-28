package com.example.jpabook.controller;

import com.example.jpabook.domain.Address;
import com.example.jpabook.domain.Member;
import com.example.jpabook.domain.MemberRole;
import com.example.jpabook.dto.MemberDto;
import com.example.jpabook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {



    private final MemberService memberService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
