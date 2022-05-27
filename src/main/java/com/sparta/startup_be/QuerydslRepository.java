package com.sparta.startup_be;

import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.SharedOffice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuerydslRepository {
    int countCityQuery(String city,String monthly,int depositlimt,int feelimit);
    int countGuQuery(String city,String monthly,int depositlimt,int feelimit);
    int countDongQuery(String city,String monthly,int depositlimt,int feelimit);

    int countAllByQuery(String city,String keyword,int depositlimt,int feelimit);

    List<String> findDongQuery(double minX,double maxX,double minY,double maxY);
    List<String> findGuQuery(double minX,double maxX,double minY,double maxY);
    List<String> findCityQuery(double minX,double maxX,double minY,double maxY);

    List<Estate> searchAllByQuery(String city,String keyword,int start,String monthly,int depositlimit,int feelimit);

    //SharedOffice Repository
    List<String> findSharedOfficebyCityQuery(double minX,  double maxX, double minY, double maxY);

    List<String> findSharedOfficebyGuQuery(double minX,  double maxX, double minY, double maxY);

    List<String> findSharedOfficebyDongQuery(double minX,  double maxX, double minY, double maxY);


    int countSharedOfficeByQuery(String city,String keyword);

    List<SharedOffice> searchSharedOfficeByQuery(String city,String keyword,int start);




}
