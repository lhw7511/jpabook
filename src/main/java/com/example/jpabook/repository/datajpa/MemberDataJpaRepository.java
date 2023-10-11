package com.example.jpabook.repository.datajpa;

import com.example.jpabook.domain.Member;
import com.example.jpabook.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberDataJpaRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.name= :name")
    List<Member> findByName(@Param("name") String username);


    @Query("select new com.example.jpabook.dto.MemberDto(m.id,m.email,m.name) from Member m where m.name= :name")
    MemberDto findByNameDto(@Param("name") String username);

    @Query("select m.name from Member m where m.id= :id")
    String findByIdValue(@Param("id")Long id);

    Member findByEmail(String email);

    @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") List<String> names);





}
