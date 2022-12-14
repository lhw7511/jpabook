package com.example.jpabook.repository.datajpa;

import com.example.jpabook.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberDataJpaRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);

    Member findByEmail(String email);
}
