package com.sparta.startup_be;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.QCoordinateEstate;
import com.sparta.startup_be.model.QEstate;

import javax.persistence.EntityManager;
import java.util.List;


public class QuerydslRepositoryImpl implements QuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public QuerydslRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    //해당 좌쵸 내 시,구,동 조회
    @Override
    public int countCityQuery(String city,String monthly,String depositlimt,String feelimit) {
        QEstate qEstate = new QEstate("e");
        int count = (int) queryFactory
                .from(qEstate)
                .select(qEstate)
                .where(qEstate.city.contains(city)
                        .and(qEstate.monthly.contains(monthly)))
                .stream()
                .count();
        return count;
    }
    @Override
    public int countGuQuery(String city,String monthly,String depositlimt,String feelimit) {
        QEstate qEstate = new QEstate("e");
        int count = (int) queryFactory.
                from(qEstate)
                .select(qEstate)
                .where(qEstate.gu.contains(city)
                        .and(qEstate.monthly.contains(monthly)))
                .stream()
                .count();
        return count;
    }
    @Override
    public int countDongQuery(String city,String monthly,String depositlimt,String feelimit) {
        QEstate qEstate = new QEstate("e");
        int count = (int) queryFactory
                .from(qEstate)
                .select(qEstate)
                .where(qEstate.dong.contains(city)
                        .and(qEstate.monthly.contains(monthly)))
                .stream().count();
        return count;
    }

    @Override
    public List<String> findDongQuery(double minX, double maxX, double minY, double maxY) {
        QEstate qEstate = new QEstate("e");
        QCoordinateEstate qCoordinateEstate = new QCoordinateEstate("q");
        return queryFactory.from(qEstate)
                .select(qEstate.dong)
                .join(qCoordinateEstate)
                .on(qEstate.id.eq(qCoordinateEstate.estateid))
                .where(qCoordinateEstate.x.between(minX,maxX)
                                .and(qCoordinateEstate.y.between(minY,maxY)))
                .distinct().fetch();
    }
    @Override
    public List<String> findGuQuery(double minX, double maxX, double minY, double maxY) {
        QEstate qEstate = new QEstate("e");
        QCoordinateEstate qCoordinateEstate = new QCoordinateEstate("q");
        return queryFactory.from(qEstate)
                .select(qEstate.gu)
                .join(qCoordinateEstate)
                .on(qEstate.id.eq(qCoordinateEstate.estateid))
                .where(qCoordinateEstate.x.between(minX,maxX)
                        .and(qCoordinateEstate.y.between(minY,maxY)))
                .distinct().fetch();
    }
    @Override
    public List<String> findCityQuery(double minX, double maxX, double minY, double maxY) {
        QEstate qEstate = new QEstate("e");
        QCoordinateEstate qCoordinateEstate = new QCoordinateEstate("q");
        return queryFactory.from(qEstate)
                .select(qEstate.city)
                .join(qCoordinateEstate)
                .on(qEstate.id.eq(qCoordinateEstate.estateid))
                .where(qCoordinateEstate.x.between(minX,maxX)
                        .and(qCoordinateEstate.y.between(minY,maxY)))
                .distinct().fetch();
    }

}
