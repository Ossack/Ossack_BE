package com.sparta.startup_be;

import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.model.Estate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuerydslRepository {
    int countCityQuery(String city,String monthly,int depositlimt,int feelimit);
    int countGuQuery(String city,String monthly,int depositlimt,int feelimit);
    int countDongQuery(String city,String monthly,int depositlimt,int feelimit);

    int countAllByQuery(@Param("keyword") String city);

    List<String> findDongQuery(double minX,double maxX,double minY,double maxY);
    List<String> findGuQuery(double minX,double maxX,double minY,double maxY);
    List<String> findCityQuery(double minX,double maxX,double minY,double maxY);

    List<Estate> searchAllByQuery(String city,int start,String monthly,int depositlimit,int feelimit);

    //SharedOffice Repository
    @Query(nativeQuery = true,value = "select distinct e.city from shared_office e," +
            " coordinate_shared_office c  where e.id=c.sharedofficeid  and c.x between :minX and :maxX and c.y between :minY and :maxY")
    List<String> findSharedOfficebyCityQuery(double minX,  double maxX, double minY, double maxY);
    @Query(nativeQuery = true,value = "select distinct e.gu from shared_office e," +
            " coordinate_shared_office c  where e.id=c.sharedofficeid  and c.x between :minX and :maxX and c.y between :minY and :maxY")
    List<String> findSharedOfficebyGuQuery(double minX,  double maxX, double minY, double maxY);
    @Query(nativeQuery = true,value = "select distinct e.dong from shared_office e," +
            " coordinate_shared_office c  where e.id=c.sharedofficeid  and c.x between :minX and :maxX and c.y between :minY and :maxY")
    List<String> findSharedOfficebyDongQuery(double minX,  double maxX, double minY, double maxY);



}
