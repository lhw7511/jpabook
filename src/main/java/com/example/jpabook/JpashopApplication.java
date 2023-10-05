package com.example.jpabook;

import com.example.jpabook.auth.MyMemberDetail;
import com.example.jpabook.domain.Address;
import com.example.jpabook.domain.Member;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//수정일 생성일 자동화
@EnableJpaAuditing
@SpringBootApplication

public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
		Hello hello = new Hello();
		hello.setData("TEST");
		System.out.println(hello.getData());

	}

	@Bean
	Hibernate5Module hibernate5Module(){
		return new Hibernate5Module();
	}

	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em){
		return new JPAQueryFactory(em);
	}

	@Bean
	public AuditorAware<String> auditorProvider(){
		return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication ==  null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
					return Optional.empty();
				}
				MyMemberDetail myMemberDetail = (MyMemberDetail) authentication.getPrincipal();
				return Optional.of(myMemberDetail.getUsername());
			}
		};
	}

}
