package com.sparta.startup_be.sharedOffice;

import com.sparta.startup_be.QuerydslRepository;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.SharedOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharedOfficeRepository extends JpaRepository<SharedOffice,Long>, QuerydslRepository {

    //지역 리스트 갯수
    List<SharedOffice> findSharedOfficeByName(String name);
    @Query("select count(u) from SharedOffice u where u.city like :keyword")
    int countAllByCityQuery(@Param("keyword")String city);

    @Query("select count(u) from SharedOffice u where u.gu like :keyword")
    int countAllByGuQuery(@Param("keyword")String gu);

    @Query("select count(u) from SharedOffice u where u.dong like :keyword")
    int countAllByDongQuery(@Param("keyword")String dong);

}
