package com.example.jpabook.controller;

import com.example.jpabook.auth.MyMemberDetail;
import com.example.jpabook.dto.testDto;
import com.example.jpabook.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String home(Model model, Authentication authentication){
        if(authentication != null){
            MyMemberDetail myMemberDetail = (MyMemberDetail)authentication.getPrincipal();  //userDetail 객체를 가져옴
            model.addAttribute("memberEmail", myMemberDetail.getUsername());
        }
        return "home";
    }

    @RequestMapping("/accessDenied")
    public String home(){
        return "accessDenied";
    }

    @RequestMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,Model model){
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "loginForm.html";
    }

    @RequestMapping("/jsonTest")
    public @ResponseBody String test(@RequestBody testDto testDto){
        System.out.println(testDto.getName());
        return testDto.getName();
    }
}
