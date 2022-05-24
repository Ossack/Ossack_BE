package com.sparta.startup_be.estate;

import com.sparta.startup_be.QuerydslRepository;
import com.sparta.startup_be.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;


public interface EstateRepository extends JpaRepository<Estate, Long>, QuerydslRepository {

    @Query("select u from Estate u where u.city like :keyword")
    List<Estate> searchAllByCity(@Param("keyword") String city);


    @Query("select u from Estate u where u.dong like :keyword")
    List<Estate> searchAllBydong(@Param("keyword") String city);

    @Query("select avg (u.rent_fee) from Estate u where u.dong like :keyword group by u.dong")
    float dongAvgQuery(@Param("keyword") String dong);

    @Query("select avg (u.rent_fee) from Estate u where u.gu like :keyword group by u.gu")
    float guAvgQuery(@Param("keyword") String gu);

    @Query("select avg (u.rent_fee) from Estate u where u.city like :keyword group by u.city")
    float cityAvgQuery(@Param("keyword") String city);

    @Query("select avg (u.area) from Estate u where u.dong like :keyword group by u.dong")
    float dongAreaAvgQuery(@Param("keyword") String dong);

    @Query("select avg (u.area) from Estate u where u.gu like :keyword group by u.gu")
    float guAvgAreaQuery(@Param("keyword") String gu);

    @Query("select avg (u.area) from Estate u where u.city  like :keyword group by u.city")
    float cityAreaAvgQuery(@Param("keyword") String city);

}
