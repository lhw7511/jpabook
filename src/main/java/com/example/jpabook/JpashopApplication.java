package com.example.jpabook;

import com.example.jpabook.domain.Address;
import com.example.jpabook.domain.Member;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

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

}
