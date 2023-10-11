package com.example.jpabook.controller;


import com.example.jpabook.domain.Member;
import com.example.jpabook.dto.MemberDto;
import com.example.jpabook.repository.MemberRepository;
import com.example.jpabook.repository.datajpa.MemberDataJpaRepository;
import com.example.jpabook.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ExampleController {


    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final MemberDataJpaRepository memberDataJpaRepository;

    @PostMapping("/exception")
    public void exceptionTest() throws Exception {
        throw new Exception();
    }

    @PostMapping("/nullException")
    public void exceptionTest2() throws Exception {
        throw new NullPointerException();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String,String>> ExcetionHandler(Exception e){
        log.info("지역 컨트롤러에서 예외처리");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("error type", httpStatus.getReasonPhrase());
        resultMap.put("code","400");
        resultMap.put("message","에러발생");

        return ResponseEntity.status(httpStatus).body(resultMap);
    }


    @GetMapping("/interceptor/include")
    public String interceptorTest(){
        return "Include";
    }

    @GetMapping("/interceptor/exclude")
    public String interceptorTest2(){
        return "Exclude";
    }

    @GetMapping("/dirtyCheckingTest")
    public List<Member> dirtyCheckingTest(){
        Member memberOne = memberRepository.findOne(1L);
        memberService.dirtyCheckingTest(memberOne);
        List<Member> members = memberRepository.findAll();
        memberOne.setName("updateName2");
        return members;
    }

    @GetMapping("queryMethodDtoTest")
    public MemberDto queryMethodDtoTest(String name){
        return memberDataJpaRepository.findByNameDto(name);
    }

    @GetMapping("queryMethodValueTest")
    public String queryMethodValueTest(Long id){
        return memberDataJpaRepository.findByIdValue(id);
    }

    @GetMapping("collectionParamBindingTest")
    public List<Member> collectionParamBindingTest(){
        return  memberDataJpaRepository.findByNames(Arrays.asList("userA","userB"));
    }
}
