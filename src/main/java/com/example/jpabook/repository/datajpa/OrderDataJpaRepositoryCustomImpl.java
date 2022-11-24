package com.example.jpabook.repository.datajpa;

import com.example.jpabook.domain.*;
import com.example.jpabook.repository.OrderSearch;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.jpabook.domain.QDelivery.*;
import static com.example.jpabook.domain.QMember.*;
import static com.example.jpabook.domain.QOrder.*;

@RequiredArgsConstructor
public class OrderDataJpaRepositoryCustomImpl implements OrderDataJpaRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    private BooleanExpression nameLike(String memberName) {
        if(!StringUtils.hasText(memberName)){
            return null;
        }
        return member.name.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        if(orderStatus == null){
            return null;
        }
        return order.status.eq(orderStatus);
    }


    @Override
    public Page<Order> findOrders(OrderSearch orderSearch,Pageable pageable) {

        List<Order> content = queryFactory
                .select(order)
                .from(order)
                .join(order.member, member)
                .fetchJoin()
                .join(order.delivery,delivery)
                .fetchJoin()
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> total = queryFactory
                .select(order.count())
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()));
        return PageableExecutionUtils.getPage(content,pageable,total::fetchOne);
    }
}
