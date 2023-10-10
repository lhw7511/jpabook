package com.example.jpabook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@NamedQuery(
        name="Member.findByName",
        query="select m from Member m where m.username = :username"
)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(length = 200)
    private String pw;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;


    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;


    @Builder
    public Member(String email, String pw, String name, Address address, List<Order> orders, MemberRole memberRole) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.address = address;
        this.orders = orders;
        this.memberRole = memberRole;
    }
}