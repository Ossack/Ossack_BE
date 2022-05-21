package com.sparta.startup_be.sharedOffice;

import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.SharedOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharedOfficeRepository extends JpaRepository<SharedOffice,Long> {

    //level별 지역
    @Query(nativeQuery = true,value = "select distinct e.city from shared_office e," +
            " coordinate_shared_office c  where e.id=c.sharedofficeid  and c.x between :minX and :maxX and c.y between :minY and :maxY")
    List<String> findCityQuery(@Param("minX") float minX, @Param("maxX") float maxX, @Param("minY") float minY, @Param("maxY") float maxY);
    @Query(nativeQuery = true,value = "select distinct e.gu from shared_office e," +
            " coordinate_shared_office c  where e.id=c.sharedofficeid  and c.x between :minX and :maxX and c.y between :minY and :maxY")
    List<String> findGuQuery(@Param("minX") float minX, @Param("maxX") float maxX, @Param("minY") float minY, @Param("maxY") float maxY);
    @Query(nativeQuery = true,value = "select distinct e.dong from shared_office e," +
            " coordinate_shared_office c  where e.id=c.sharedofficeid  and c.x between :minX and :maxX and c.y between :minY and :maxY")
    List<String> findDongQuery(@Param("minX") float minX, @Param("maxX") float maxX, @Param("minY") float minY, @Param("maxY") float maxY);

    //지역 리스트 갯수
    @Query("select count(u) from SharedOffice u where u.city like :keyword")
    int countAllByCityQuery(@Param("keyword")String city);

    @Query("select count(u) from SharedOffice u where u.gu like :keyword")
    int countAllByGuQuery(@Param("keyword")String gu);

    @Query("select count(u) from SharedOffice u where u.dong like :keyword")
    int countAllByDongQuery(@Param("keyword")String dong);

    //지역별 평균



    //지역 선택후 list 보내기
    @Query(nativeQuery = true,value = "select u.* from shared_office u where u.city like :keyword order by u.id limit 10 offset :start")
    List<SharedOffice> searchAllByCityQuery(@Param("keyword")String city, @Param("start")int start);
    @Query(nativeQuery = true,value = "select u.* from shared_office u where u.dong like :keyword order by u.id limit 10 offset :start")
    List<SharedOffice> searchAllByDongQuery(@Param("keyword")String city, @Param("start")int start);
    @Query(nativeQuery = true,value = "select u.* from shared_office u where u.gu like :keyword order by u.id limit 10 offset :start")
    List<SharedOffice> searchAllByGuQuery(@Param("keyword")String city, @Param("start")int start);
}
