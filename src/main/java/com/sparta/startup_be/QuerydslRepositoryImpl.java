package com.sparta.startup_be;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.model.*;

import javax.persistence.EntityManager;
import java.util.List;


public class QuerydslRepositoryImpl implements QuerydslRepository {
    private final JPAQueryFactory queryFactory;
    public QuerydslRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    //해당 좌쵸 내 시,구,동 조회
    @Override
    public int countCityQuery(String city,String monthly,int depositlimit,int feelimit) {
        QEstate qEstate = new QEstate("e");
        int count = (int) queryFactory
                .from(qEstate)
                .select(qEstate)
                .where(qEstate.city.contains(city)
//                        .and(qEstate.monthly.contains(monthly)
                                .and(qEstate.deposit.between(0,depositlimit)
                                        .and(qEstate.rent_fee.between(0,feelimit))))
                .stream()
                .count();
        return count;
    }
    @Override
    public int countGuQuery(String city,String monthly,int depositlimit,int feelimit) {
        QEstate qEstate = new QEstate("e");
        int count = (int) queryFactory.
                from(qEstate)
                .select(qEstate)
                .where(qEstate.gu.contains(city)
//                        .and(qEstate.monthly.contains(monthly)
                                .and(qEstate.deposit.between(0,depositlimit)
                                        .and(qEstate.rent_fee.between(0,feelimit))))
                .stream()
                .count();
        return count;
    }
    @Override
    public int countDongQuery(String city,String monthly,int depositlimit,int feelimit) {
        QEstate qEstate = new QEstate("e");
        int count = (int) queryFactory
                .from(qEstate)
                .select(qEstate)
                .where(qEstate.dong.contains(city)
//                        .and(qEstate.monthly.contains(monthly)
                                .and(qEstate.deposit.between(0,depositlimit)
                                        .and(qEstate.rent_fee.between(0,feelimit))))
//                                .and(qEstate.rent_fee.)))
                .stream().count();

        return count;
    }

    @Override
    public int countAllByQuery(String city,int depositlimit,int feelimit) {
        QEstate qEstate = new QEstate("e");
        return (int) queryFactory
                .from(qEstate)
                .select(qEstate)
                .where((qEstate.city.contains(city)
                        .or(qEstate.dong.contains(city)
                                .or(qEstate.gu.contains(city))
//                                        .and(qEstate.monthly.contains(monthly)
                                .and(qEstate.deposit.between(0,depositlimit)
                                        .and(qEstate.rent_fee.between(0,feelimit))))))
                .stream().count();
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

    @Override
    public List<Estate> searchAllByQuery(String city,int start,String monthly,int depositlimit,int feelimit) {
        QEstate qEstate = new QEstate("q");
        return queryFactory.from(qEstate)
                .select(qEstate)
                .where((qEstate.city.contains(city)
                        .or(qEstate.dong.contains(city)
                                .or(qEstate.gu.contains(city))
//                                        .and(qEstate.monthly.contains(monthly)
                                            .and(qEstate.deposit.between(0,depositlimit)
                                                .and(qEstate.rent_fee.between(0,feelimit))))))
                .limit(10).offset(start)
                .fetch();
    }

    @Override
    public List<String> findSharedOfficebyCityQuery(double minX, double maxX, double minY, double maxY) {
        QSharedOffice qSharedOffice = new QSharedOffice("s");
        QCoordinateSharedOffice qCoordinateSharedOffice = new QCoordinateSharedOffice("q");
        return queryFactory.from(qSharedOffice)
                .select(qSharedOffice.city)
                .join(qCoordinateSharedOffice)
                .on(qSharedOffice.id.eq(qCoordinateSharedOffice.sharedofficeid))
                .where(qCoordinateSharedOffice.x.between(minX,maxX)
                        .and(qCoordinateSharedOffice.y.between(minY,maxY)))
                .distinct().fetch();
    }

    @Override
    public List<String> findSharedOfficebyGuQuery(double minX, double maxX, double minY, double maxY) {
        QSharedOffice qSharedOffice = new QSharedOffice("s");
        QCoordinateSharedOffice qCoordinateSharedOffice = new QCoordinateSharedOffice("q");
        return queryFactory.from(qSharedOffice)
                .select(qSharedOffice.gu)
                .join(qCoordinateSharedOffice)
                .on(qSharedOffice.id.eq(qCoordinateSharedOffice.sharedofficeid))
                .where(qCoordinateSharedOffice.x.between(minX,maxX)
                        .and(qCoordinateSharedOffice.y.between(minY,maxY)))
                .distinct().fetch();
    }

    @Override
    public List<String> findSharedOfficebyDongQuery(double minX, double maxX, double minY, double maxY) {
        QSharedOffice qSharedOffice = new QSharedOffice("s");
        QCoordinateSharedOffice qCoordinateSharedOffice = new QCoordinateSharedOffice("q");
        return queryFactory.from(qSharedOffice)
                .select(qSharedOffice.dong)
                .join(qCoordinateSharedOffice)
                .on(qSharedOffice.id.eq(qCoordinateSharedOffice.sharedofficeid))
                .where(qCoordinateSharedOffice.x.between(minX,maxX)
                        .and(qCoordinateSharedOffice.y.between(minY,maxY)))
                .distinct().fetch();
    }

    @Override
    public int countSharedOfficeByQuery(String city) {
        QSharedOffice qSharedOffice = new QSharedOffice("e");
        return (int) queryFactory
                .from(qSharedOffice)
                .select(qSharedOffice)
                .where(qSharedOffice.city.contains(city)
                        .or(qSharedOffice.dong.contains(city)
                                .or(qSharedOffice.gu.contains(city))))
                .stream().count();
    }

    @Override
    public List<SharedOffice> searchSharedOfficeByQuery(String city, int start) {
        QSharedOffice qSharedOffice = new QSharedOffice("q");
        return queryFactory.from(qSharedOffice)
                .select(qSharedOffice)
                .where(qSharedOffice.city.contains(city)
                        .or(qSharedOffice.dong.contains(city)
                                .or(qSharedOffice.gu.contains(city)
                                        .or(qSharedOffice.name.contains(city)))))
                .limit(10).offset(start)
                .fetch();
    }


}
