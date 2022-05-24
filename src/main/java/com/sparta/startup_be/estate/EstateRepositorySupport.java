package com.sparta.startup_be.estate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.startup_be.model.Estate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstateRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public EstateRepositorySupport(JPAQueryFactory queryFactory){
        super(Estate.class);
        this.queryFactory =queryFactory;
    }

//    public List<Estate> findByName(String name){
//        return queryFactory
//                .selectFrom(Estate);
//    }
}
