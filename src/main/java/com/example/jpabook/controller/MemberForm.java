package com.example.jpabook.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원이름은 필수입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;
    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String pw;
    private String memberRole;
}
