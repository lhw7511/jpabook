package com.example.jpabook;

import com.example.jpabook.domain.Address;
import com.example.jpabook.domain.Member;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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

}
