package com.example.jpabook.repository.datajpa;

import com.example.jpabook.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberDataJpaRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.name= :name")
    List<Member> findByName(@Param("name") String username);


}
